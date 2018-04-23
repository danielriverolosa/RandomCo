package com.rivero.daniel.randomco.infrastructure.di.module

import com.rivero.daniel.randomco.data.datasource.api.ApiClientGenerator
import com.rivero.daniel.randomco.data.datasource.api.RetrofitApiClientGenerator
import com.rivero.daniel.randomco.data.repository.user.UserDataRepository
import com.rivero.daniel.randomco.domain.repository.UserRepository
import com.rivero.daniel.randomco.infrastructure.AndroidApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ApplicationModule(private val application: AndroidApplication) {

    @Provides
    @Singleton
    fun providesApplicationContext(): AndroidApplication {
        return application
    }

    @Provides
    @Singleton
    internal fun provideApiClientGenerator(apiClientGenerator: RetrofitApiClientGenerator): ApiClientGenerator {
        return apiClientGenerator
    }

    @Provides
    @Singleton
    fun providesUserRepository(userDataRepository: UserDataRepository): UserRepository = userDataRepository

}