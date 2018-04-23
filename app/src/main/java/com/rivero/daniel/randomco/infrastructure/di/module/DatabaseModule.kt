package com.rivero.daniel.randomco.infrastructure.di.module

import android.arch.persistence.room.Room
import com.rivero.daniel.randomco.data.datasource.local.RandomDatabase
import com.rivero.daniel.randomco.infrastructure.AndroidApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(application: AndroidApplication) {

    private var database: RandomDatabase = Room.databaseBuilder(application, RandomDatabase::class.java, "random-db")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun providesDatabase(): RandomDatabase = database

    @Singleton
    @Provides
    fun providesUserDao(database: RandomDatabase) = database.getUserDao()

}