package com.example.weatherapp.screens.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.weatherapp.utils.Cities
import com.example.weatherapp.utils.LocationManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.models.Weather
import com.example.weatherapp.screens.adapters.ForecastRVAdapter
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val forecastRVAdapter = ForecastRVAdapter(arrayListOf())

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    lateinit var mainViewModel: MainViewModel

    private val locationManager = LocationManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initViewModel()

        initForecastRV()

        initCitiesSpinner()

        locationManager.checkLocationPermission()
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel::class.java)
        binding.viewModel = mainViewModel
        binding.executePendingBindings()
    }

    private fun initForecastRV() {
        binding.forecastRv.layoutManager = LinearLayoutManager(this)
        binding.forecastRv.adapter = forecastRVAdapter
        mainViewModel.forecast.observe(
            this,
            Observer<ArrayList<Weather>> {
                it?.let { forecastRVAdapter.replaceData(it) }
            }
        )
    }

    private fun initCitiesSpinner() {
        val cities = Cities.values()
        val citiesNames = cities.map { it.cityName }
        binding.citySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, citiesNames)
        binding.citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                when (cities[pos]) {
                    Cities.CURRENT_LOCATION -> locationManager.checkLocationPermission()
                    else -> mainViewModel.loadWeather(cities[pos].id)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        locationManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}