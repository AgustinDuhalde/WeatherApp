package com.example.weatherapp.repositories

import com.example.weatherapp.Config
import com.example.weatherapp.models.ForecastResponse
import com.example.weatherapp.models.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRemoteRepository @Inject constructor(private var weatherService: WeatherService) {

    fun getForecast(lat: Double, long: Double): Observable<ForecastResponse> {
        return weatherService.getForecast(lat, long)
    }

    fun getCurrentWeather(lat: Double, long: Double): Observable<WeatherResponse> {
        return weatherService.getCurrentWeather(lat, long)
    }

    fun getForecast(id: Int): Observable<ForecastResponse> {
        return weatherService.getForecast(id)
    }

    fun getCurrentWeather(id: Int): Observable<WeatherResponse> {
        return weatherService.getCurrentWeather(id)
    }
}

interface WeatherService {

    @GET("forecast")
    fun getForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric",
        @Query("lang") es: String = "es",
        @Query("appid") appId: String = Config.API_KEY
    ): Observable<ForecastResponse>

    @GET("weather")
    fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric",
        @Query("lang") es: String = "es",
        @Query("appid") appId: String = Config.API_KEY
    ): Observable<WeatherResponse>

    @GET("forecast")
    fun getForecast(
        @Query("id") id: Int,
        @Query("units") units: String = "metric",
        @Query("lang") es: String = "es",
        @Query("appid") appId: String = Config.API_KEY
    ): Observable<ForecastResponse>

    @GET("weather")
    fun getCurrentWeather(
        @Query("id") id: Int,
        @Query("units") units: String = "metric",
        @Query("lang") es: String = "es",
        @Query("appid") appId: String = Config.API_KEY
    ): Observable<WeatherResponse>
}