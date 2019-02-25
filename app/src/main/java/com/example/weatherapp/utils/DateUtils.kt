package com.example.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {
        fun stringToDate(string: String): Date {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return format.parse(string)
        }

        fun dateToString(date: Date): String {
            val format = SimpleDateFormat("E dd/MM HH:mm")
            return format.format(date)
        }
    }
}