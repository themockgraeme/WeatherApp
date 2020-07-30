package com.geektastic.weatherapp.tasks

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Callable
import java.util.concurrent.Executors

/**
 * Simple task runner class used to launch an asynchronous operation.
 *
 * Copied from the original Java at:
 *
 * https://stackoverflow.com/questions/58767733/android-asynctask-api-deprecating-in-android-11-what-are-the-alternatives
 *
 * This is being used because the AsyncTask library is being deprecated, and I didn't want to write
 * a complex threading class from scratch!
 */
class TaskRunner <R> {

    /**
     * Executes the task and passes the result back.
     *
     * @param callable The [Callable] to execute
     * @param callback The [TaskCallback] interface to pass the result back to
     */
    fun <R> execute(callable: Callable<R>, callback: TaskCallback <R>) {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        executor.execute {
            val result: R = callable.call()

            handler.post { callback.onResult(result) }
        }
    }
}
