package com.example.pogodynka

import retrofit2.awaitResponse

class PogodynkaRepository {
    companion object {
        suspend fun getWeather(city: String, appid: String): Data? =
                PogodynkaService.api.getWeather(city,appid)
                        .awaitResponse().body()
    }
}