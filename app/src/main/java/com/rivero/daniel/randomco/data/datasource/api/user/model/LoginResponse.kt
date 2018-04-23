package com.rivero.daniel.randomco.data.datasource.api.user.model


class LoginResponse(
        val username: String,
        val password: String,
        val salt: String?,
        val md5: String?,
        val sha1: String?,
        val sha256: String?
)