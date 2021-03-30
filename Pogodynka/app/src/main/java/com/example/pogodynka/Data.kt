package com.example.pogodynka

data class Data(
        val coord: Coord,
        val weather: List<Weather>,
        val base: String,
        val main: Main,
        val visibility: Int,
        val wind: Wind,
        val clouds: Clouds,
        val dt: Int,
        val sys: Sys,
        val timezone: Int,
        val id: Int,
        val name: String,
        val cod: Int
)

data class Coord(
        val lat: Double,
        val lon: Double
)

data class Weather(
        val description: String,
        val icon: String,
        val id: Int,
        val main: String
)

data class Main(
        val feels_like: Double,
        val humidity: Int,
        val pressure: Int,
        val temp: Double,
        val temp_max: Double,
        val temp_min: Double
)

data class Wind(
        val deg: Int,
        val gust: Double,
        val speed: Double
)

data class Clouds(
        val all: Int
)

data class Sys(
        val country: String,
        val id: Int,
        val sunrise: Long,
        val sunset: Long,
        val type: Int
)
