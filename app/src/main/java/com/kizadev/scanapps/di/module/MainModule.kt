package com.kizadev.scanapps.di.module

import android.app.Application
import android.content.Context
import com.kizadev.domain.repository.AppRepository
import com.kizadev.domain.repository.SettingsRepository
import com.kizadev.scanapps.di.component.AppScope
import com.kizadev.scanapps.repository.ScanAppsRepository
import dagger.Binds
import dagger.Module

@Module
interface AppBinds {

    @Binds
    @AppScope
    fun context(application: Application): Context

    @Binds
    @AppScope
    fun bindsScanAppsRepositoryToAppRepository(scanAppsRepository: ScanAppsRepository): AppRepository

    @Binds
    @AppScope
    fun bindsScanAppsRepositoryToSettingsRepository(scanAppsRepository: ScanAppsRepository): SettingsRepository
}

@Module(includes = [AppBinds::class])
abstract class MainModule
