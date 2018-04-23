package com.rivero.daniel.randomco.data.datasource.local.user.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "User")
class UserEntity (
        @PrimaryKey val username: String,
        val name: String,
        val surname: String,
        val gender: String,
        val email: String,
        val phone: String,
        val registered: Long,
        @Embedded val location: LocationEntity,
        @Embedded val picture: PictureEntity?,
        var favorite: Boolean = false
)