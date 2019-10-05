package com.example.starwars

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface  ApiService{

    @GET("people/")
    fun getPeople(): Call<JsonObject>
    @GET("planets/")
    fun getPlanets(): Call<JsonObject>
}