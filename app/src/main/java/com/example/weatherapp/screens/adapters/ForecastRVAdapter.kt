package com.example.weatherapp.screens.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.weatherapp.databinding.WeatherItemBinding
import com.example.weatherapp.models.Weather
import java.util.*

class ForecastRVAdapter(
    private var items: ArrayList<Weather>
) : RecyclerView.Adapter<ForecastRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = WeatherItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    fun replaceData(newItems: ArrayList<Weather>) {
        items = newItems
        notifyDataSetChanged()
    }

    class ViewHolder(private var binding: WeatherItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(weather: Weather) {
            binding.weather = weather
            binding.executePendingBindings()
        }
    }

}