package com.geektastic.weatherapp.data

import org.junit.Test

import org.junit.Assert.*

/**
 * Tests for the distance method in the coordinates class.
 */
class CoordinatesUnitTest {

	companion object {
		/**
		 * Since doubles aren't always equal due to rounding, this value is used to give a range so
		 * that small errors introduced in calculations don't don't cause the testt to fail.
		 */
		private const val DELTA = 0.1

		/**
		 * The radius of the Earth.
		 */
		private const val RADIUS = 6371.0
	}

	/**
	 * Basic test to ensure that two coordinates with the same point have no distance between them.
	 */
	@Test
	fun distance_samePoint() {
		val coordinates = Coordinates(0.0, 0.0)
		val distance = coordinates.distance(coordinates)

		assertEquals("Expected distance to be 0", 0.0, distance, DELTA)
	}

	/**
	 * Tests whether the distance between a pair of antipodal points on the Equator is correct.
	 */
	@Test
	fun distance_antipodalPoints() {
		val point1 = Coordinates(0.0, 0.0)
		val point2 = Coordinates(0.0, 180.0)
		val distance = point1.distance(point2)
		val expected = RADIUS * Math.PI

		assertEquals("Incorrect distance at Equator", expected, distance, DELTA)
	}

	/**
	 * Tests whether the distance between a pair of antipodal points on the Equator is correct.
	 *
	 * This failed the first time because I thought that the Poles were at 180 degrees, not 90!
	 */
	@Test
	fun distance_poles() {
		val point1 = Coordinates(90.0, 0.0)
		val point2 = Coordinates(-90.0, 0.0)
		val distance = point1.distance(point2)
		val expected = RADIUS * Math.PI

		assertEquals("Incorrect distance between Poles", expected, distance, DELTA)
	}
}
