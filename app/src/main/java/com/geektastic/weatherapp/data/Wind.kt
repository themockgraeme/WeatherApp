package com.geektastic.weatherapp.data

import com.google.gson.annotations.SerializedName

data class Wind (
    val speed: Double,
    @SerializedName("deg")
    val degrees: Int)
