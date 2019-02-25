package com.example.weatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherResponse (
    @Expose @SerializedName("name") val cityName: String,
    @Expose val weather: ArrayList<Condition>,
    @Expose val main: MainWeather
)

data class Condition (
    @Expose val description: String,
    @Expose val icon: String
)

data class MainWeather (
    @Expose var temp: Float,
    @Expose val pressure: String,
    @Expose val humidity: String
)