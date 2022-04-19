package com.example.memelegends.utils

import com.example.memelegends.entities.Meme
import com.example.memelegends.entities.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST

interface MemeInterface {
    @POST("/memes/create")
    fun createMeme( @Body meme: Meme): Call<MemeResponse>
    @GET("/memes/all")
    fun getAll(): Call<List<Meme>>
}