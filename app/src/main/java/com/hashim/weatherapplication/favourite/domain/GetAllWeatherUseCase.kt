package com.hashim.weatherapplication.favourite.domain

import com.hashim.weatherapplication.favourite.data.db.WeatherDatabaseRepository
import com.hashim.weatherapplication.favourite.data.db.WeatherEntity
import javax.inject.Inject

class GetAllWeatherUseCase @Inject constructor(
    private val repository: WeatherDatabaseRepository,
) {
    suspend operator fun invoke(): List<WeatherEntity> {
        return repository.getAllWeather()
    }
}