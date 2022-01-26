package com.kizadev.scanapps.app

import android.app.Application
import android.content.Context
import com.kizadev.scanapps.di.component.AppComponent
import com.kizadev.scanapps.di.component.DaggerAppComponent

class ScanApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}

val Context.appComponent: AppComponent
    get() = if (this is ScanApplication) appComponent
    else this.applicationContext.appComponent
