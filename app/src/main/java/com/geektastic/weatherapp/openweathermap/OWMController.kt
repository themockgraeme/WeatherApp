package com.geektastic.weatherapp.openweathermap

import android.util.Log
import com.geektastic.weatherapp.data.Weather
import com.google.gson.GsonBuilder
import com.kizio.imgurtest.imgur.OWMService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Controller object for initiating a download of weather from OpenWeatherMap.
 *
 * @author Graeme Sutherland
 * @since 10/12/2019
 * @constructor Creates an instance of ImgurController.
 * @param listener The [OWMListener] used to receive the images
 */
class OWMController (private val listener: OWMListener) : Callback<Weather> {

	companion object {
		/**
		 * Logging [TAG] for debug messages.
		 */
		private val TAG = OWMController::class.java.name

		/**
		 * The base [URL] for the OpenWeatherMap API.
		 */
		private const val URL = "https://api.openweathermap.org/"

		/**
		 * The API key used to access the OpenWeatherMap services.
		 */
		private const val API_KEY = "ae84012a1da7c6e9fe2456ae8232ede7"
	}

	/**
	 * Invoked to get the weather for the specified location
	 */
	fun getWeather(city: String) {
		val gson = GsonBuilder()
			.setLenient()
			.create()
		val retrofit = Retrofit.Builder()
			.baseUrl(URL)
			.addConverterFactory(GsonConverterFactory.create(gson))
			.build()
		val owmService = retrofit.create(OWMService::class.java)
		val call = owmService.getWeather(city, API_KEY)

		call.enqueue(this)
	}

	/**
	 * Invoked when a network exception occurred talking to the server or when an unexpected
	 * exception occurred creating the request or processing the response.
	 *
	 * @param call The [Call] object used to make the request
	 * @param t The [Throwable] containing the error data
	 */
	override fun onFailure(call: Call<Weather>, t: Throwable) {
		Log.e(TAG, "Failed to retrieve weather data", t)
	}

	/**
	 * Invoked for a received HTTP response.
	 *
	 * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
	 * Call [Response.isSuccessful] to determine if the response indicates success.
	 *
	 * @param call The [Call] object used to make the request
	 * @param response The [Response] from the server
	 */
	override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
		if (response.isSuccessful) {
			response.body()?.let {
				listener.onReceiveWeatherData(it)
			}
		}
	}
}
