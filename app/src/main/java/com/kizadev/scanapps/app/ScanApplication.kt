package com.kizadev.scanapps.app

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.kizadev.scanapps.const.PREFS_NAME
import com.kizadev.scanapps.di.component.AppComponent
import com.kizadev.scanapps.di.component.DaggerAppComponent

class ScanApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .application(this)
            .dataStore(dataStore)
            .build()
    }
}

val Context.dataStore by preferencesDataStore(name = PREFS_NAME)

val Context.appComponent: AppComponent
    get() = if (this is ScanApplication) appComponent
    else this.applicationContext.appComponent
