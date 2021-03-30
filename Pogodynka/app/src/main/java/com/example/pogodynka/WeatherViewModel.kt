package com.example.pogodynka

import android.app.Application
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class WeatherViewModel(application: Application): AndroidViewModel(application) {

    private val APIKEY = "44aabd474b348759a1861441f789d54a"

    private val _weather: MutableLiveData<Data> = MutableLiveData()
    val weather: LiveData<Data>
    get() = _weather

    fun setWeather(city: String) {
        viewModelScope.launch {
            _weather.value = PogodynkaRepository.getWeather(city, APIKEY)
        }
    }

    private val _query: MutableLiveData<String> = MutableLiveData()
    val query: LiveData<String>
    get() = _query

    fun setQuery(Query: String) {
        viewModelScope.launch {
            _query.postValue(query.toString())
        }
    }

    fun convertTime(epoc: Long): String? {
        return try {
            val sdf = SimpleDateFormat("H:mm")
            val netDate = Date(epoc * 1000)
            sdf.format(netDate).toString()
        } catch (e: Exception) {
            e.toString()
        }
    }

    fun calcTemp(t: Double) = (t - 273.15).roundToInt()

    var icon: Int? = null
    var desc: String? = null

    fun fetchWeather(weather: Weather, activity: FragmentActivity) {
        when (weather.icon) {
            "01d", "01n" -> {
                icon = R.drawable.ic_sun
                desc = "Słonecznie"
            }

            "02d", "02n" -> {
                icon = R.drawable.ic_sun_cloud
                desc = "Częściowe zachmurzenie"
            }

            "03d", "03n",  "04d", "04n" -> {
                icon = R.drawable.ic_cloud
                desc = "Całkowite zachmurzenie"
            }

            "09d", "09n" -> {
                icon = R.drawable.ic_rain
                desc = "Silne opady deszczu"
            }

            "10d", "10n" -> {
                icon = R.drawable.ic_sun_rain
                desc = "Częściowe opady deszczu"
            }

            "11d", "11n" -> {
                icon = R.drawable.ic_weather_lightning
                desc = "Burza"
            }
            "13d", "13n" -> {
                icon = R.drawable.ic_snow
                desc = "Opady śniegu"
            }
            "50d", "50n" -> {
                icon = R.drawable.ic_mist
                desc = "Mgła"
            }
        }
    }
}