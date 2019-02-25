package com.example.weatherapp.screens.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.util.Log
import com.example.weatherapp.models.ForecastResponse
import com.example.weatherapp.models.Weather
import com.example.weatherapp.models.WeatherResponse
import com.example.weatherapp.repositories.WeatherRepository
import com.example.weatherapp.utils.DateUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.math.round

class MainViewModel(private var weatherRepository: WeatherRepository) : ViewModel() {

    val currentWeather = ObservableField<WeatherResponse>()

    val isLoading = ObservableField(true)

    val forecast = MutableLiveData<ArrayList<Weather>>()

    private val compositeDisposable = CompositeDisposable()

    fun loadWeather(lat: Double, long: Double) {
        loadCurrentWeather(weatherRepository.getCurrentWeather(lat, long))
        loadForecast(weatherRepository.getForecast(lat, long))
    }

    fun loadWeather(id: Int) {
        loadCurrentWeather(weatherRepository.getCurrentWeather(id))
        loadForecast(weatherRepository.getForecast(id))
    }

    fun loadForecast(observable: Observable<ForecastResponse>) {
        compositeDisposable.add(
            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoading.set(true) }
                .doOnComplete { isLoading.set(false) }
                .doOnError { isLoading.set(false) }
                .subscribe(
                    {
                        it.list.map {
                            val date = DateUtils.stringToDate(it.dtText)
                            it.dtText = DateUtils.dateToString(date)
                            it.main.temp = round(it.main.temp)
                        }
                        forecast.value = it.list
                    },
                    { Log.d("ERROR", "FORECAST REQUEST ERROR", it) }
                )
        )
    }

    fun loadCurrentWeather(observable: Observable<WeatherResponse>) {
        compositeDisposable.add(
            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoading.set(true) }
                .doOnComplete { isLoading.set(false) }
                .doOnError { isLoading.set(false) }
                .subscribe(
                    {
                        it.main.temp = round(it.main.temp)
                        currentWeather.set(it)
                    },
                    { Log.d("ERROR", "CURRENT LOCATION WEATHER REQUEST ERROR", it) }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}