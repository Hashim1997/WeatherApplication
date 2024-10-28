package com.hashim.weatherapplication.favourite.data.db

import javax.inject.Inject

interface WeatherDatabaseRepository {
    suspend fun addWeather(weatherEntity: WeatherEntity)
    suspend fun removeWeather(weatherEntity: WeatherEntity)
    suspend fun getAllWeather(): List<WeatherEntity>
}

class WeatherDatabaseRepositoryImpl @Inject constructor(
    private val weatherDao: WeatherDao
) : WeatherDatabaseRepository {
    override suspend fun addWeather(weatherEntity: WeatherEntity) {
        weatherDao.insertAll(weatherEntity)
    }

    override suspend fun removeWeather(weatherEntity: WeatherEntity) {
        weatherDao.delete(weatherEntity)
    }

    override suspend fun getAllWeather(): List<WeatherEntity> {
        return weatherDao.getAll()
    }
}