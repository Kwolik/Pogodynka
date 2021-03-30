package com.example.pogodynka

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PogodynkaAPI {
    @GET("weather?")
    fun getWeather(
            @Query("q") city: String,
            @Query("appid") appid: String
    ): Call<Data>
}