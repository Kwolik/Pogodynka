package com.example.pogodynka

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_normal.*
import java.text.SimpleDateFormat
import java.util.*

class NormalFragment : Fragment(){

    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)

        return inflater.inflate(R.layout.fragment_normal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setWeather("Ruda Slaska")
        viewModel.setQuery("Ruda Slaska")

        viewModel.weather.observe(viewLifecycleOwner, Observer {
            fetchData(viewModel.weather)
            if (searchViewCity.query.isNullOrEmpty())
                searchViewCity.setQuery("Ruda Slaska", false)
        })

        searchViewCity.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewModel.setWeather(query)
                    viewModel.setQuery(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (!query.isNullOrBlank())
                    if (!query[0].isUpperCase())
                        searchViewCity.setQuery(query.capitalize(), false)
                return false
            }
        })
    }

    private fun fetchData(response: LiveData<Data>) {
        val resp = response.value

        if(resp == null)
        {
            Toast.makeText(context, "Niepoprawne miasto", Toast.LENGTH_SHORT).show()
        }
        else
        {
            viewModel.fetchWeather(resp?.weather!!.first(), requireActivity())

            val temp = resp.main.temp
            val press = resp.main.pressure
            val sunrise = resp.sys.sunrise
            val sunset = resp.sys.sunset
            val tempMin = resp.main.temp_min
            val tempMax = resp.main.temp_max

            val currentTime = Calendar.getInstance().time
            val format1 = SimpleDateFormat("dd/MM/yyyy")
            val format2 = SimpleDateFormat("HH:mm")
            val newDate = format1.format(currentTime)
            val newTime = format2.format(currentTime)


            temperature.text = "${viewModel.calcTemp(temp)}°C"
            textViewPress.text = "$press hPa"
            imageViewMainImage.setImageResource(viewModel.icon!!)
            textViewDateSunup.text = viewModel.convertTime(sunrise)
            textViewDateSundown.text = viewModel.convertTime(sunset)
            imageView3.setImageResource(R.drawable.ic_sunset)
            imageView4.setImageResource(R.drawable.ic_sunset_down)
            textViewDesc.text = viewModel.desc
            textViewTempMin.text = "${viewModel.calcTemp(tempMin)}°C"
            textViewTempMax.text = "${viewModel.calcTemp(tempMax)}°C"
            textViewDate.text = newDate
            textViewHour.text = newTime
        }
    }
}