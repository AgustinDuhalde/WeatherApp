package com.example.weatherapp

import com.example.weatherapp.models.*
import com.example.weatherapp.repositories.WeatherRepository
import com.example.weatherapp.screens.main.MainViewModel
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.BeforeClass
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainViewModelTest : WeatherRepository {

    companion object {
        @BeforeClass
        @JvmStatic
        fun setupClass() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        }
    }

    private val forecastEmitter = PublishSubject.create<ForecastResponse>()
    private val weatherEmitter = PublishSubject.create<WeatherResponse>()

    private val mainViewModel = MainViewModel(this)

    private val fakeCondition = Condition("description", "icon")
    private val fakeMainWeather = MainWeather(24.0F, "1007", "40")
    private val fakeForecastResponse = ForecastResponse(
        arrayListOf(
            Weather(
                "2019-02-25 03:00:00",
                arrayListOf(fakeCondition),
                fakeMainWeather
            )
        )
    )
    private val fakeWeatherResponse = WeatherResponse(
        "Buenos Aires",
        arrayListOf(fakeCondition),
        fakeMainWeather
    )


    @Test
    fun getWeatherByLocationSuccess() {
        assertEquals(true, mainViewModel.isLoading.get())

        mainViewModel.loadWeather(35.000, 50.000)

        weatherEmitter.onNext(fakeWeatherResponse)
        forecastEmitter.onNext(fakeForecastResponse)

        weatherEmitter.onComplete()
        forecastEmitter.onComplete()

        assertEquals(false, mainViewModel.isLoading.get())
        assertEquals(fakeWeatherResponse, mainViewModel.currentWeather.get())

        mainViewModel.forecast.observeForever {
            assertEquals(fakeForecastResponse.list, it)
        }
    }

    @Test
    fun getWeatherByIdSuccess() {
        assertEquals(true, mainViewModel.isLoading.get())

        mainViewModel.loadWeather(0)

        weatherEmitter.onNext(fakeWeatherResponse)
        forecastEmitter.onNext(fakeForecastResponse)

        weatherEmitter.onComplete()
        forecastEmitter.onComplete()

        assertEquals(false, mainViewModel.isLoading.get())
        assertEquals(fakeWeatherResponse, mainViewModel.currentWeather.get())

        mainViewModel.forecast.observeForever {
            assertEquals(fakeForecastResponse.list, it)
        }
    }

    @Test
    fun getWeatherByLocationError() {
        assertEquals(true, mainViewModel.isLoading.get())

        mainViewModel.loadWeather(35.000, 50.000)

        weatherEmitter.onError(RuntimeException())
        forecastEmitter.onError(RuntimeException())

        weatherEmitter.onComplete()
        forecastEmitter.onComplete()

        assertEquals(false, mainViewModel.isLoading.get())
        assertNotEquals(fakeWeatherResponse, mainViewModel.currentWeather.get())

        mainViewModel.forecast.observeForever {
            assertNotEquals(fakeForecastResponse.list, it)
        }
    }

    @Test
    fun getWeatherByIdError() {
        assertEquals(true, mainViewModel.isLoading.get())

        mainViewModel.loadWeather(0)

        weatherEmitter.onError(RuntimeException())
        forecastEmitter.onError(RuntimeException())

        weatherEmitter.onComplete()
        forecastEmitter.onComplete()

        assertEquals(false, mainViewModel.isLoading.get())
        assertNotEquals(fakeWeatherResponse, mainViewModel.currentWeather.get())

        mainViewModel.forecast.observeForever {
            assertNotEquals(fakeForecastResponse.list, it)
        }
    }

    override fun getForecast(lat: Double, long: Double): Observable<ForecastResponse> {
        return forecastEmitter
    }

    override fun getCurrentWeather(lat: Double, long: Double): Observable<WeatherResponse> {
        return weatherEmitter
    }

    override fun getForecast(id: Int): Observable<ForecastResponse> {
        return forecastEmitter
    }

    override fun getCurrentWeather(id: Int): Observable<WeatherResponse> {
        return weatherEmitter
    }
}
