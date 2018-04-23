package com.rivero.daniel.randomco.data.datasource.api.user

import com.rivero.daniel.randomco.data.datasource.api.user.model.UserListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface UserApi {

    @GET("/api")
    fun getUsers(@Query("results") results: Int = 40): Call<UserListResponse>

}