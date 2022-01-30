package com.kizadev.scanapps.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.kizadev.scanapps.R
import com.kizadev.scanapps.app.appComponent
import com.kizadev.scanapps.databinding.FragmentScanBinding
import com.kizadev.scanapps.presentation.adapters.AppsAdapter
import com.kizadev.scanapps.presentation.adapters.ItemOffsetDecoration
import com.kizadev.scanapps.presentation.viewmodel.MainViewModel
import com.kizadev.scanapps.presentation.viewmodel.factory.ScanViewModelFactory
import com.kizadev.scanapps.presentation.viewmodel.state.ScanScreen
import javax.inject.Inject

class ScanFragment : Fragment(R.layout.fragment_scan), View.OnClickListener {

    private lateinit var viewBinding: FragmentScanBinding

    private val viewModel: MainViewModel by activityViewModels {
        viewModelFactory.create()
    }

    private val appsAdapter = AppsAdapter()

    @Inject
    lateinit var viewModelFactory: ScanViewModelFactory.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = FragmentScanBinding.inflate(inflater)

        setup()

        lifecycleScope.launchWhenStarted {
            viewModel.screenState.collect {
                Log.e("Fragment", "onCreateView: $it",)
                renderData(it)
            }
        }

        return viewBinding.root
    }

    private fun setup() {
        with(viewBinding) {
            rvApps.layoutManager = GridLayoutManager(context, 2)
            rvApps.adapter = appsAdapter
            rvApps.addItemDecoration(ItemOffsetDecoration())

            scanView.setOnClickListener(this@ScanFragment)
        }
    }

    private fun renderData(state: ScanScreen) {
        when {
            state.isScanning -> {
                viewBinding.scanView.startAnimateScanning()
            }

            state.isScanFailed -> {
                Toast.makeText(context, "${state.scanFailedMsg}", Toast.LENGTH_SHORT).show()
            }

            state.appList.isNotEmpty() -> {
                appsAdapter.submitList(state.appList)
                showAppList()
            }
        }
    }

    private fun showAppList() {
        with(viewBinding) {
            rvApps.visibility = View.VISIBLE
            scanView.finishAnimateScanning()
            appImage.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        v ?: return
        when (v.id) {
            viewBinding.scanView.id -> {
                viewModel.handleScanning()
            }
        }
    }
}
