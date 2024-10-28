package com.hashim.weatherapplication.favourite.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hashim.weatherapplication.home.data.models.WeatherResponse

@Entity
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val location: String,
    val temperature: String,
    val weatherIcon: String,
    val windSpeed: String,
    val humidity: String,
)

fun WeatherResponse.toFavouriteModel(): WeatherEntity = WeatherEntity(
    id = 0,
    location = this.data?.request?.firstOrNull()?.query.toString(),
    temperature = "${this.data?.currentCondition?.firstOrNull()?.tempC}°",
    weatherIcon = this.data?.currentCondition?.firstOrNull()?.weatherIconUrl?.firstOrNull()?.value.orEmpty(),
    windSpeed = "${this.data?.currentCondition?.firstOrNull()?.windspeedKmph} km/h",
    humidity = "${this.data?.currentCondition?.firstOrNull()?.humidity}%",
)