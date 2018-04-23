package com.rivero.daniel.randomco.domain

import java.io.Serializable
import java.util.*


data class User(
        val username: String,
        val name: String,
        val surname: String,
        val gender: String,
        val email: String,
        val phone: String,
        val registered: Date,
        val location: Location,
        var picture: UserPicture? = null,
        var favorite: Boolean = false
): Serializable {
    fun getCompleteName(): String = name.capitalize() + " " + surname.capitalize()

    fun hasCoordinates(): Boolean = location.longitude != null && location.latitude != null
}