package com.geektastic.weatherapp.data

import com.google.gson.annotations.SerializedName
import kotlin.math.*

/**
 * Holds the latitude and longitude of a location.
 *
 * @param latitude The latitude of a location as a [Double]
 * @param longitude The longitude of a location as a [Double]
 */
data class Coordinates (
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double) {

    companion object {

        /**
         * The average radius of the Earth in kilometres. It actually varies between 6378 km at the
         * Equator and 6357 km at the Poles.
         */
        private const val RADIUS = 6371.0
    }
    /**
     * Calculates the distance between a point on the Earth represented by this set of coordinates,
     * and another.
     *
     * Strictly speaking, I don't need to calculate the distance in kilometres, as the relative
     * magnitudes would do.
     *
     * I'm using the Haversine formula, which treats the Earth as a perfect sphere. This isn't
     * entirely accurate, as it's actually an oblate spheroid. There are better approximations, but
     * this is a quick and dirty piece of code for a coding test.
     *
     * The algorithm is based on the JavaScript implementation given here.
     *
     * https://www.movable-type.co.uk/scripts/latlong.html
     *
     * My knowledge of spherical geometry isn't sufficient to derive or check this, so I'm taking it
     * on faith...
     *
     * @param other The point as a [Coordinates] object to calculate the distance to
     * @return The distance in kilometres as a [Double]
     */
    fun distance(other: Coordinates) : Double {
        val deltaLatitude = Math.toRadians(other.latitude - this.latitude)
        val deltaLongitude = Math.toRadians(other.longitude - this.longitude)
        val thisLatitude = Math.toRadians(this.latitude)
        val otherLatitude = Math.toRadians(other.latitude)

        val a = sin(deltaLatitude / 2).pow(2) +
                (cos(thisLatitude) * cos(otherLatitude) *
                sin(deltaLongitude / 2).pow(2))

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return c * RADIUS
    }
}