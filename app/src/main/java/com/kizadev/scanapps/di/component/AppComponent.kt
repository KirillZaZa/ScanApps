package com.kizadev.scanapps.di.component

import android.app.Application
import com.kizadev.scanapps.di.module.MainModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope

@Component(modules = [MainModule::class])
@AppScope
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope
