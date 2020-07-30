package com.geektastic.weatherapp.data

import com.google.gson.annotations.SerializedName

/**
 * Data object that represents a city.
 *
 * @param id The city's ID
 * @param name The city's name
 * @param country The city's country code
 * @param coordinates The city's latitude and longitude as a [Coordinates] object
 */
data class City (
    @SerializedName("_id")
    val id: Int,
    val name: String,
    val country: String,
    @SerializedName("coord")
    val coordinates: Coordinates) {

    /**
     * Gets the name and country of the city, used to access the OpenWeatherMap API.
     *
     * @return The name of the city and it country as a [String] in the format "London,UK"
     */
    fun getNameAndCountry() : String {
        return this.name + "," + this.country
    }
}
