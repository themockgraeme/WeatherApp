package com.geektastic.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import com.geektastic.weatherapp.data.City
import com.geektastic.weatherapp.data.CityList
import com.geektastic.weatherapp.data.Coordinates
import com.geektastic.weatherapp.data.Weather
import com.geektastic.weatherapp.openweathermap.OWMListener
import com.geektastic.weatherapp.openweathermap.OWMController
import com.geektastic.weatherapp.tasks.CityLoaderTask
import com.geektastic.weatherapp.tasks.TaskCallback
import com.geektastic.weatherapp.tasks.TaskRunner
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.round

class MainActivity : AppCompatActivity(), TaskCallback<CityList>, OWMListener, OnSuccessListener<Location> {

	companion object {
		/**
		 * Code to identify a request to access the device's location service.
		 */
		private const val LOCATION_REQUEST_CODE = 101

		/**
		 * Converts a temperature in Kelvin to degrees Celsius.
		 */
		private const val KELVIN_TO_CELSIUS = -273.15
	}

	/**
	 * Provides the location services for the app. Just for the record, it's possible for lateinit
	 * to receive a null value, so I don't entirely trust it.
	 */
	private lateinit var fusedLocationClient: FusedLocationProviderClient

	/**
	 * Holds the list of [City] objects as an [Array]. It might be more sensible to load them into a
	 * Room database (or similar), rather than reading into memory. It runs okay in the emulator, so
	 * I'm leaving it as is, since premature optimisation is the root of all evil.
	 */
	private var cities: CityList? = null

	/**
	 * The [Coordinates] of the user's location.
	 */
	private var coordinates: Coordinates? = null

	/**
	 * Invoked when the app is created.
	 *
	 * @param savedInstanceState A [Bundle] containing any saved state
	 */
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

		setContentView(R.layout.activity_main)

		loadCities()

		requestLocation()
	}

	/**
	 * Callback from registering permissions.
	 *
	 * @param requestCode The code passed with the permissions request to identify if
	 * @param permissions The permissions requested
	 * @param grantResults Indicates which permissions were granted
	 */
	override fun onRequestPermissionsResult(
		requestCode: Int,
		permissions: Array<out String>,
		grantResults: IntArray) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)

		if (requestCode == LOCATION_REQUEST_CODE && grantResults.size >= 2
			&& grantResults[0] == PackageManager.PERMISSION_GRANTED
			&& grantResults[1] == PackageManager.PERMISSION_GRANTED
		) {
			requestLocation()
		}
	}

	/**
	 * Helper method to request the device's last location.
	 */
	private fun requestLocation() {
		if (ActivityCompat.checkSelfPermission(
				this,
				Manifest.permission.ACCESS_FINE_LOCATION
			) == PackageManager.PERMISSION_GRANTED
			&& ActivityCompat.checkSelfPermission(
				this,
				Manifest.permission.ACCESS_COARSE_LOCATION
			) == PackageManager.PERMISSION_GRANTED
		) {
			enable_location.visibility = View.GONE
			weather_data.visibility = View.VISIBLE

			fusedLocationClient.lastLocation.addOnSuccessListener (this)
		} else {
			val permissions = arrayOf(
				Manifest.permission.ACCESS_FINE_LOCATION,
				Manifest.permission.ACCESS_COARSE_LOCATION
			)

			enable_location.visibility = View.VISIBLE
			weather_data.visibility = View.GONE

			ActivityCompat.requestPermissions(this, permissions, LOCATION_REQUEST_CODE)
		}
	}

	/**
	 * Loads the city list.
	 */
	private fun loadCities() {
		val task = CityLoaderTask(this.applicationContext)
		val runner = TaskRunner<Array<City>>()

		runner.execute(task, this)
	}

	/**
	 * Called to get the weather from the nearest city.
	 */
	private fun getWeather() {
		cities?.let { cityList ->
			coordinates?.let { location ->
				val city = cityList.getNearestCity(location)

				if (city != null) {
					val controller = OWMController(this)

					controller.getWeather(city.getNameAndCountry())
				}
			}
		}
	}

	/**
	 * Called when the cities list is loaded.
	 *
	 * @param result The results as an object of type [CityList]
	 */
	override fun onResult(result: CityList) {
		this.cities = result

		getWeather()
	}

	/**
	 * Invoked when the weather data is received.
	 *
	 * @param weatherData The received [Weather] object from the server
	 */
	override fun onReceiveWeatherData(weatherData: Weather) {
		val observedTemperature = weatherData.data.temperature + KELVIN_TO_CELSIUS

		this.city.text = weatherData.name
		this.temperature.text = getString(R.string.temperature, observedTemperature)
	}

	/**
	 * Invoked when the app receives a location value.
	 *
	 * @param location The [Location] of the device
	 */
	override fun onSuccess(location: Location?) {
		if (location != null) {
			this.coordinates = Coordinates(location.latitude, location.longitude)

			getWeather()
		}
	}
}
