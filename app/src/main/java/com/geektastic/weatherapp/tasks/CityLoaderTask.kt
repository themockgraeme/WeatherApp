package com.geektastic.weatherapp.tasks

import android.content.Context
import com.geektastic.weatherapp.R
import com.geektastic.weatherapp.data.City
import com.geektastic.weatherapp.data.CityList
import com.google.gson.Gson
import java.io.InputStreamReader
import java.lang.ref.WeakReference
import java.util.concurrent.Callable

/**
 * [Callable] used to load the list of cities into the  app.
 *
 * @param context The [Context] to use
 */
class CityLoaderTask(context: Context) : Callable<CityList> {

    /**
     * A [WeakReference] to the [Context] object to prevent leakages
     */
    private val contextReference = WeakReference<Context>(context)

    /**
     * Reads the city data in from the file.
     */
    override fun call(): CityList {
        var cities: Array<City>? = null

        contextReference.get()?.let {
            val gson = Gson()
            val resources = it.resources
            val reader = InputStreamReader(resources.openRawResource(R.raw.city_list))
            cities = gson.fromJson<Array<City>>(reader, Array<City>::class.java)

            reader.close()
        }

        return CityList(cities)
    }
}
