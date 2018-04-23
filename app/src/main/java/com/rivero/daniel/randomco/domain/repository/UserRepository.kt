package com.rivero.daniel.randomco.domain.repository

import com.rivero.daniel.randomco.domain.User


interface UserRepository {

    fun getUserList(): List<User>

    fun registerUser(user: User)

    fun deleteUser(user: User)

    fun updateUser(user: User)

    fun refreshData()

}