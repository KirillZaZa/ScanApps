package com.kizadev.scanapps.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.kizadev.scanapps.R
import com.kizadev.scanapps.app.appComponent
import com.kizadev.scanapps.databinding.FragmentScanBinding
import com.kizadev.scanapps.ext.isDestinationsTheSame
import com.kizadev.scanapps.presentation.adapters.AppsAdapter
import com.kizadev.scanapps.presentation.adapters.decor.ItemOffsetDecoration
import com.kizadev.scanapps.presentation.adapters.listeners.OnAppListener
import com.kizadev.scanapps.presentation.viewmodel.MainViewModel
import com.kizadev.scanapps.presentation.viewmodel.ScanEvent
import com.kizadev.scanapps.presentation.viewmodel.factory.MainViewModelFactory
import com.kizadev.scanapps.presentation.viewmodel.state.ScanScreen
import com.kizadev.scanapps.utils.asAppDetails
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScanFragment : Fragment(R.layout.fragment_scan), View.OnClickListener {

    private lateinit var viewBinding: FragmentScanBinding

    private val viewModel: MainViewModel by activityViewModels {
        viewModelFactory.create()
    }

    private val onAppListener = OnAppListener { appInfo, imageView, textView, root ->
        if (isDestinationsTheSame(DetailsFragment::class.java)) return@OnAppListener

        val extras = FragmentNavigatorExtras(
            imageView to requireContext().getString(R.string.transition_icon, appInfo.packageName),
            textView to requireContext().getString(R.string.transition_text, appInfo.name),
            root to requireContext().getString(R.string.transition_root, appInfo.size),
        )

        val directions: NavDirections = ScanFragmentDirections.actionScanFragmentToDetailsFragment(
            appInfo.asAppDetails()
        )

        findNavController().navigate(
            directions,
            extras
        )
    }

    private val appsAdapter = AppsAdapter(onAppListener)

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentScanBinding.inflate(inflater, container, false)

        sharedElementReturnTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
                .apply {
                    interpolator = FastOutSlowInInterpolator()
                }

        lifecycleScope.launchWhenStarted {
            viewModel.screenState.collect {
                renderState(it)
            }
        }

        viewModel.eventsFlow
            .flowWithLifecycle(
                lifecycle = viewLifecycleOwner.lifecycle,
                minActiveState = Lifecycle.State.STARTED
            )
            .onEach {
                renderEvents(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        with(viewBinding) {

            rvApps.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = appsAdapter
                addItemDecoration(ItemOffsetDecoration())
                postponeEnterTransition()
                doOnPreDraw {
                    startPostponedEnterTransition()
                }
            }
            scanView.setOnClickListener(this@ScanFragment)
        }
    }

    private fun renderEvents(event: ScanEvent) {
        when (event) {
            is ScanEvent.ShowFailure -> {
                Toast.makeText(requireContext(), event.msg, Toast.LENGTH_SHORT).show()
            }

            is ScanEvent.ShowApps -> {
                showAppsWithAnimations()
            }
        }
    }

    private fun showAppsWithAnimations() {
        lifecycleScope.launch {
            hideViews {
                viewModel.handleListCanBeShown()
            }
        }
    }

    private fun hideViews(onEnd: () -> Unit) {
        with(viewBinding) {
            scanView.finishAnimateScanning()

            ViewCompat.animate(appImage)
                .setDuration(500)
                .translationY(-1000f)
                .setInterpolator(FastOutSlowInInterpolator())
                .withEndAction {
                    viewBinding.appImage.visibility = View.GONE
                    onEnd.invoke()
                }
                .start()
        }
    }

    private fun renderState(state: ScanScreen) {
        with(viewBinding) {
            if (state.isScanning) {
                scanView.startAnimateScanning()
            } else scanView.cancelAnimateScanning()

            if (state.isListCanBeShown) {
                appsAdapter.submitList(viewModel.screenState.value.appList)

                appImage.visibility = View.GONE
                scanView.visibility = View.GONE
                rvApps.visibility = View.VISIBLE
            }
        }
    }

    override fun onClick(v: View?) {
        v ?: return
        when (v.id) {
            viewBinding.scanView.id -> {
                if (viewModel.screenState.value.isScanning) {
                    viewModel.handleCancelScanning()
                } else viewModel.handleScanning()
            }
        }
    }
}
