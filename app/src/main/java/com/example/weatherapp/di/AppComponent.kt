package com.example.weatherapp.di

import com.example.weatherapp.WeatherApp
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, MainActivityModule::class, NetModule::class])
interface AppComponent : AndroidInjector<WeatherApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<WeatherApp>()
}