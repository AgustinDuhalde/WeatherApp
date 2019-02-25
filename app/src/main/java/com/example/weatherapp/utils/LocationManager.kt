package com.example.weatherapp.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.example.weatherapp.screens.main.MainActivity
import com.google.android.gms.location.LocationServices

class LocationManager(private val activity: MainActivity) {

    private val MY_PERMISSIONS_REQUEST_COARSE_LOCATION = 1234


    fun checkLocationPermission() {
        if (!hasLocationPermission())
            requestLocationPermission()
        else
            getLastKnownLocation()

    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            MY_PERMISSIONS_REQUEST_COARSE_LOCATION
        )
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        fusedLocationClient.lastLocation.apply {

            addOnSuccessListener { location: Location? ->
                location?.let { activity.mainViewModel.loadWeather(it.latitude, it.longitude) }
            }

            addOnFailureListener {
                loadDefaultWeather()
            }

        }
    }

    private fun loadDefaultWeather() {
        activity.mainViewModel.loadWeather(Cities.BUENOS_AIRES.id)
    }

    private fun hasLocationPermission() =
        ContextCompat.checkSelfPermission(
            activity.applicationContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_COARSE_LOCATION -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    getLastKnownLocation()
                else
                    loadDefaultWeather()
            }
        }
    }
}