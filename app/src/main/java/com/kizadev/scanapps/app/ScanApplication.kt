package com.kizadev.scanapps.app

import android.app.Application
import android.content.Context
import com.kizadev.scanapps.di.component.AppComponent
import com.kizadev.scanapps.di.component.DaggerAppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class ScanApplication : Application() {

    lateinit var appComponent: AppComponent

    lateinit var scope: CoroutineScope

    override fun onCreate() {
        super.onCreate()

        scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}

val Context.scope: CoroutineScope
    get() =
        if (this is ScanApplication) scope
        else this.applicationContext.scope

val Context.appComponent: AppComponent
    get() = if (this is ScanApplication) appComponent
    else this.applicationContext.appComponent
