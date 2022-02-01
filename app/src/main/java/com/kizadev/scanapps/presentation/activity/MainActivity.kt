package com.kizadev.scanapps.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kizadev.scanapps.R
import com.kizadev.scanapps.app.appComponent
import com.kizadev.scanapps.databinding.ActivityMainBinding
import com.kizadev.scanapps.presentation.viewmodel.MainViewModel
import com.kizadev.scanapps.presentation.viewmodel.factory.MainViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main), View.OnClickListener {

    private val viewBinding by viewBinding { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by viewModels {
        viewModelFactory.create()
    }

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent.inject(this)

        lifecycleScope.launchWhenCreated {
            viewModel.themeState.collect {
                renderTheme(it.isDarkMode)
            }
        }

        setContentView(viewBinding.root)

        lifecycleScope.launch {
            viewModel.themeState.collect {
                delay(10)
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
        viewBinding.switchTheme.switch(mode = isDarkMode)
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
