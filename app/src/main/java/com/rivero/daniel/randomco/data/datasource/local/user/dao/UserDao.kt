package com.rivero.daniel.randomco.data.datasource.local.user.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.rivero.daniel.randomco.data.datasource.local.user.entity.UserEntity


@Dao
interface UserDao {

    @Query("SELECT * FROM User") fun getUsers(): List<UserEntity>

    @Query("SELECT * FROM User WHERE username = :username") fun getUserByUsername(username: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertUser(user: UserEntity)

    @Query("DELETE FROM User WHERE username = :username") fun deleteUserByUsername(username: String)

    @Query("DELETE FROM User") fun deleteUsers()

}