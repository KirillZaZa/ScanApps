package com.kizadev.scanapps.di.module

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.kizadev.domain.repository.AppRepository
import com.kizadev.domain.repository.SettingsRepository
import com.kizadev.scanapps.di.component.AppScope
import com.kizadev.scanapps.repository.ScanAppsRepository
import dagger.Binds
import dagger.Module

@Module
@AppScope
interface AppBinds {

    @Binds
    @AppScope
    fun context(application: Application): Context

    @Binds
    @AppScope
    fun dataStore(context: Context): DataStore<Preferences>

    @Binds
    @AppScope
    fun bindsScanAppsRepositoryToAppRepository(scanAppsRepository: ScanAppsRepository): AppRepository

    @Binds
    @AppScope
    fun bindsScanAppsRepositoryToSettingsRepository(scanAppsRepository: ScanAppsRepository): SettingsRepository
}

@Module(includes = [AppBinds::class])
abstract class MainModule
