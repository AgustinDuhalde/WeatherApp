package com.example.weatherapp.di

import com.example.weatherapp.repositories.WeatherRepository
import com.example.weatherapp.screens.main.MainActivity
import com.example.weatherapp.screens.main.MainViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class MainActivityModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        internal fun providesMainViewModelFactory(weatherRepository: WeatherRepository) : MainViewModelFactory {
            return MainViewModelFactory(weatherRepository)
        }
    }

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity

}