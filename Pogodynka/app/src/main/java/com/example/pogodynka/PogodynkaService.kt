package com.example.pogodynka

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASEURL = "https://api.openweathermap.org/data/2.5/"

object PogodynkaService {
    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
    val api: PogodynkaAPI by lazy {
        retrofit.create(PogodynkaAPI::class.java)
    }
}