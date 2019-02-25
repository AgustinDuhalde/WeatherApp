package com.example.weatherapp.repositories

import com.example.weatherapp.models.ForecastResponse
import com.example.weatherapp.models.WeatherResponse
import io.reactivex.Observable

interface WeatherRepository {
    fun getForecast(lat: Double, long: Double) : Observable<ForecastResponse>
    fun getCurrentWeather(lat: Double, long: Double) : Observable<WeatherResponse>
    fun getForecast(id: Int) : Observable<ForecastResponse>
    fun getCurrentWeather(id: Int) : Observable<WeatherResponse>
}