package com.kizio.imgurtest.imgur

import com.geektastic.weatherapp.data.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Provides the HTTP API for the OpenWeatherMap server to access its services.
 */
interface OWMService {

	/**
	 * Gets the weather for the specified query.
	 *
	 * It would be better to hardware the APPID value, rather than passing it in with each call. But
	 * I'm not sufficiently fluent in the Retrofit API to figure it out, and it's quicker to do it
	 * like this, given it's a quick piece of test code.
	 *
	 * @param query The search [String] used to retrieve weather data
	 * @param id The app ID key [String] used to authenticate the call
	 * @return A [Call] to access the downloaded data as a [Weather] object
	 */
	@Headers("Authorization: Client-ID 3dd4b5a5d616392")
	@GET("/data/2.5/weather")
	fun getWeather(@Query("q") query: String, @Query("APPID") id: String): Call<Weather>
}
