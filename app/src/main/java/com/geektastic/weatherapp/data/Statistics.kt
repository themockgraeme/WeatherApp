package com.geektastic.weatherapp.data

import com.google.gson.annotations.SerializedName

data class Statistics (
    @SerializedName("temp")
    val temperature: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("temp_min")
    val minimumTemperature: Double,
    @SerializedName("temp_max")
    val maximumTemperature: Double,
    val pressure: Int,
    val humidity: Int)
