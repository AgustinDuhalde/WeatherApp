package com.example.weatherapp.repositories

import com.example.weatherapp.models.ForecastResponse
import com.example.weatherapp.models.WeatherResponse
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl(private val remoteDataSource: WeatherRemoteRepository) : WeatherRepository {

    // TODO - Implement local repository (maybe Room)
    // private val localDataSource = WeatherLocalRepository()

    override fun getForecast(lat: Double, long: Double) : Observable<ForecastResponse> {
        return remoteDataSource.getForecast(lat, long)
    }

    override fun getCurrentWeather(lat: Double, long: Double) : Observable<WeatherResponse> {
        return remoteDataSource.getCurrentWeather(lat, long)
    }

    override fun getForecast(id: Int) : Observable<ForecastResponse> {
        return remoteDataSource.getForecast(id)
    }

    override fun getCurrentWeather(id: Int) : Observable<WeatherResponse> {
        return remoteDataSource.getCurrentWeather(id)
    }
}
