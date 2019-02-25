package com.example.weatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @Expose val list: ArrayList<Weather>
)

data class Weather(
    @Expose @SerializedName("dt_txt") var dtText: String,
    @Expose val weather: ArrayList<Condition>,
    @Expose val main: MainWeather
)