package com.example.memelegends.utils

import com.example.memelegends.entities.User
import com.squareup.moshi.Json
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserInterface {

    @POST("/users/register")
    fun register( @Body user: User): Call<UserResponse>
    @POST("/users/login")
    fun login( @Body user: User): Call<UserResponse>
}