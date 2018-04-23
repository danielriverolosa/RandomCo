package com.rivero.daniel.randomco.infrastructure.di.component

import com.rivero.daniel.randomco.infrastructure.AndroidApplication
import com.rivero.daniel.randomco.infrastructure.di.module.ApplicationModule
import com.rivero.daniel.randomco.infrastructure.di.module.DatabaseModule
import com.rivero.daniel.randomco.infrastructure.di.module.ViewModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
            ApplicationModule::class,
            DatabaseModule::class
        ]
)
interface ApplicationComponent {

    fun inject(application: AndroidApplication)

    fun viewComponent(viewModule: ViewModule): ViewComponent
}