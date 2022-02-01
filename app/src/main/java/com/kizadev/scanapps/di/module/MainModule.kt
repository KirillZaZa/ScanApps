package com.kizadev.scanapps.di.module

import android.app.Application
import android.content.Context
import com.kizadev.domain.repository.AppRepository
import com.kizadev.domain.repository.SettingsRepository
import com.kizadev.scanapps.repository.ScanAppsRepository
import com.kizadev.scanapps.storage.AppStorage
import com.kizadev.scanapps.storage.PrefStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface AppBinds {

    @Binds
    @Singleton
    fun context(application: Application): Context

    @Binds
    @Singleton
    fun bindsScanAppsRepositoryToAppRepository(scanAppsRepository: ScanAppsRepository): AppRepository

    @Binds
    @Singleton
    fun bindsScanAppsRepositoryToSettingsRepository(scanAppsRepository: ScanAppsRepository): SettingsRepository
}

@Module(includes = [AppBinds::class])
class MainModule {

    @Provides
    @Singleton
    fun providePrefStorage(context: Context): PrefStorage {
        return PrefStorage(context)
    }

    @Provides
    @Singleton
    fun provideAppStorage(context: Context): AppStorage {
        return AppStorage(context)
    }
}
