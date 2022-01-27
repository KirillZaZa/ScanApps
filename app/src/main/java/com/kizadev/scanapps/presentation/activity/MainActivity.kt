package com.kizadev.scanapps.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kizadev.scanapps.R
import com.kizadev.scanapps.databinding.ActivityMainBinding
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {

    private val viewBinding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        lifecycleScope.launchWhenResumed {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                delay(2000)
                viewBinding.scanView.startAnimateScanning()
                delay(2000)
                viewBinding.scanView.stopAnimateScanning()
            }
        }
    }
}
