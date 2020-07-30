package com.geektastic.weatherapp.tasks

/**
 * Provides a callback for an asynchronous task.
 *
 * @param R The type being returned as a result
 */
interface TaskCallback <R> {

    /**
     * Called when the task has a result.
     *
     * @param result The results as an object of type R
     */
    fun onResult(result: R)
}