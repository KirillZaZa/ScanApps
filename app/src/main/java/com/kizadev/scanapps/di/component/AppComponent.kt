package com.kizadev.scanapps.di.component

import android.app.Application
import com.kizadev.scanapps.di.module.MainModule
import com.kizadev.scanapps.presentation.activity.MainActivity
import com.kizadev.scanapps.presentation.fragments.DetailsFragment
import com.kizadev.scanapps.presentation.fragments.ScanFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [MainModule::class])
@Singleton
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: DetailsFragment)

    fun inject(fragment: ScanFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
