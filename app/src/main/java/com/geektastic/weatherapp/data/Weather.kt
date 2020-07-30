package com.geektastic.weatherapp.data

import com.google.gson.annotations.SerializedName

data class Weather (
    val id: Int,
    val name: String,
    @SerializedName("coord")
    val coordinates: Coordinates,
    val weather: Array<Observation>,
    val base: String,
    @SerializedName("main")
    val data: Statistics,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Int,
    val sys: System,
    val timezone: Int,
    val cod: Int
)
