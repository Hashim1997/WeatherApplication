package com.hashim.weatherapplication.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import coil3.imageLoader
import coil3.request.ImageRequest
import coil3.toBitmap
import com.hashim.weatherapplication.core.constant.NotificationConstant
import com.hashim.weatherapplication.favourite.data.db.WeatherDatabaseRepository
import com.hashim.weatherapplication.favourite.data.db.WeatherEntity
import dagger.assisted.AssistedInject
import java.lang.Exception

@HiltWorker
class NotificationWorkManager @AssistedInject constructor(
    private val repository: WeatherDatabaseRepository,
    appContext: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return super.getForegroundInfo()
    }

    override suspend fun doWork(): Result {
        return try {
            val favourites = repository.getAllWeather()
            if (favourites.isEmpty()) {
                Result.success()
            }
            favourites.forEach {
                showNotification(it)
            }
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun showNotification(entity: WeatherEntity) {
        val image = getBitmapFromUrl(applicationContext, entity.weatherIcon)
        val nBuilder = NotificationCompat.Builder(applicationContext, NotificationConstant.CHANNEL_ID)
            .setContentTitle(entity.location)
            .setContentText(entity.temperature)
            .setLargeIcon(image)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        val nManager = NotificationManagerCompat.from(applicationContext)
        nManager.notify(1, nBuilder)
    }

    suspend fun getBitmapFromUrl(context: Context, url: String): Bitmap? {
        // Coil (suspends, non-blocking, and thread safe)
        val request = ImageRequest.Builder(context)
            .data(url)
            .build()
        return context.imageLoader.execute(request).image?.toBitmap()
    }
}