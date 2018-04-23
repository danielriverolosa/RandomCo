package com.rivero.daniel.randomco.data.datasource.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.rivero.daniel.randomco.data.datasource.local.user.dao.UserDao
import com.rivero.daniel.randomco.data.datasource.local.user.entity.UserEntity

@Database(
        entities = [(UserEntity::class)],
        version = 3
)
abstract class RandomDatabase: RoomDatabase() {

    abstract fun getUserDao(): UserDao

}