package com.kizadev.scanapps.presentation.activity

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.kizadev.scanapps.R
import com.kizadev.scanapps.app.appComponent
import com.kizadev.scanapps.databinding.ActivityMainBinding
import com.kizadev.scanapps.presentation.viewmodel.MainViewModel
import com.kizadev.scanapps.presentation.viewmodel.factory.MainViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main), View.OnClickListener {

    private lateinit var viewBinding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        viewModelFactory.create()
    }

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent.inject(this)

        // hardcoded
        val root: ConstraintLayout = findViewById(R.id.activity_container)
        viewBinding = ActivityMainBinding.bind(root)

        setContentView(viewBinding.root)

        lifecycleScope.launchWhenResumed {
            viewModel.themeState.collect {
                renderTheme(it.isDarkMode)
                renderSwitchAnimation(it.isDarkMode)
            }
        }

        setup()
    }

    private fun setup() {
        viewBinding.switchTheme.setOnClickListener(this@MainActivity)
    }

    private fun renderTheme(isDarkMode: Boolean) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun renderSwitchAnimation(isDarkMode: Boolean) {
        with(viewBinding) {
            root.viewTreeObserver.addOnGlobalLayoutListener(object :
                    ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        switchTheme.switch(mode = isDarkMode)
                        root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                })
        }
    }

    override fun onClick(v: View?) {
        v ?: return
        when (v.id) {
            viewBinding.switchTheme.id -> {
                viewModel.handleAppTheme()
            }
        }
    }
}
