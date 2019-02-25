package com.example.weatherapp.utils

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.example.weatherapp.Config
import com.squareup.picasso.Picasso


@BindingAdapter("imageUrl")
fun ImageView.loadImage(imageUrl: String?) {
    Picasso.get()
        .load(Config.ICON_URL + imageUrl + ".png")
        .into(this)
}

