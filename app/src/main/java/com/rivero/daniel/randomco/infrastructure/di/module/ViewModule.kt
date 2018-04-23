package com.rivero.daniel.randomco.infrastructure.di.module

import android.app.Activity
import com.rivero.daniel.randomco.data.service.LocationGoogleService
import com.rivero.daniel.randomco.domain.service.LocationService
import com.rivero.daniel.randomco.infrastructure.di.scope.ViewScope
import com.rivero.daniel.randomco.presentation.base.BaseActivity
import com.rivero.daniel.randomco.presentation.base.Navigator
import dagger.Module
import dagger.Provides


@Module
class ViewModule(private val activity: BaseActivity) {

    @Provides
    @ViewScope
    internal fun activity(): Activity {
        return activity
    }

    @Provides
    @ViewScope
    fun providesNavigator(): Navigator = Navigator(activity)

    @Provides
    @ViewScope
    fun providesLocationService(): LocationService = LocationGoogleService(activity)

}