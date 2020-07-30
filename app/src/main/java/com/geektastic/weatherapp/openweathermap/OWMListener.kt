package com.geektastic.weatherapp.openweathermap

import com.geektastic.weatherapp.data.Weather

/**
 * Callback for receiving weather data.
 */
interface OWMListener {
    /**
     * Invoked when the weather data is received.
     *
     * @param weatherData The received [Weather] object from the server
     */
    fun onReceiveWeatherData(weatherData: Weather)
}
