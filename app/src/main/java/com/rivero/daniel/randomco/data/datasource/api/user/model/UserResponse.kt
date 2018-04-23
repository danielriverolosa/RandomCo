package com.rivero.daniel.randomco.data.datasource.api.user.model

import java.util.*


class UserResponse(
        val gender: String,
        val name: NameResponse,
        val location: LocationResponse,
        val email: String,
        val login: LoginResponse,
        val dob: String,
        val registered: Date,
        val phone: String,
        val cell: String,
        val picture: PictureResponse,
        val nat: String
)