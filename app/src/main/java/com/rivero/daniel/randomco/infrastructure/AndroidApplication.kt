package com.rivero.daniel.randomco.infrastructure

import android.app.Application
import com.rivero.daniel.randomco.BuildConfig
import com.rivero.daniel.randomco.infrastructure.di.component.ApplicationComponent
import com.rivero.daniel.randomco.infrastructure.di.component.DaggerApplicationComponent
import com.rivero.daniel.randomco.infrastructure.di.module.ApplicationModule
import com.rivero.daniel.randomco.infrastructure.di.module.DatabaseModule
import timber.log.Timber


class AndroidApplication: Application() {

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .databaseModule(DatabaseModule(this))
                .build()
    }


    override fun onCreate() {
        applicationComponent.inject(this)
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}