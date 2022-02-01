package com.kizadev.scanapps.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.kizadev.scanapps.R
import com.kizadev.scanapps.app.appComponent
import com.kizadev.scanapps.databinding.FragmentDetailsBinding
import com.kizadev.scanapps.utils.AppDetails
import java.util.concurrent.TimeUnit

class DetailsFragment : Fragment(R.layout.fragment_details), View.OnClickListener {

    private lateinit var viewBinding: FragmentDetailsBinding
    private lateinit var appDetails: AppDetails

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = requireArguments()
        appDetails = DetailsFragmentArgs.fromBundle(args).modelArg
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentDetailsBinding.inflate(inflater)

        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
                .apply {
                    interpolator = FastOutSlowInInterpolator()
                }
        postponeEnterTransition(200, TimeUnit.MILLISECONDS)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        viewBinding.buttonBack.setOnClickListener(this)
        with(viewBinding) {
            root.transitionName =
                requireContext().getString(R.string.transition_root, appDetails.size)

            val icon =
                requireContext().packageManager.getApplicationIcon(appDetails.packageName)
            appIcon.setImageDrawable(icon)
            appIcon.transitionName =
                requireContext().getString(R.string.transition_icon, appDetails.packageName)

            appName.text = appDetails.appName
            appName.transitionName =
                requireContext().getString(R.string.transition_text, appDetails.appName)

            appInstallationDate.text = appDetails.installationDate

            appSize.text = appDetails.size

            appTargetSdk.text = appDetails.targetSdk
        }
    }

    override fun onClick(v: View?) {
        v ?: return
        when (v.id) {
            viewBinding.buttonBack.id -> {
                findNavController().popBackStack()
            }
        }
    }
}
