package com.hashim.weatherapplication.search.domain

import com.hashim.weatherapplication.favourite.data.db.WeatherDatabaseRepository
import com.hashim.weatherapplication.favourite.data.db.WeatherEntity
import javax.inject.Inject

class InsertWeatherUseCase @Inject constructor(
    private val repository: WeatherDatabaseRepository,
) {
    suspend operator fun invoke(weatherEntity: WeatherEntity) {
        repository.addWeather(weatherEntity)
    }
}