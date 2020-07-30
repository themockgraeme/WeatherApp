package com.geektastic.weatherapp.data

data class System (
    val id: Int,
    val type: Int,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)
