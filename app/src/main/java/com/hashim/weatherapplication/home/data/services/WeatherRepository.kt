package com.hashim.weatherapplication.home.data.services

import com.hashim.weatherapplication.core.network.ResultState
import com.hashim.weatherapplication.home.data.models.WeatherResponse
import javax.inject.Inject

interface WeatherRepository {
    suspend fun getWeatherDetails(
        apiKey: String,
        cityName: String,
        format: String,
        showLocalTime: String,
    ): ResultState<WeatherResponse>
}

class DefaultWeatherRepository @Inject constructor(
    private val weatherService: WeatherService,
) : WeatherRepository {
    override suspend fun getWeatherDetails(
        apiKey: String,
        cityName: String,
        format: String,
        showLocalTime: String
    ): ResultState<WeatherResponse> {
        return try {
            val response = weatherService.getCityWeatherDetails(
                cityName = cityName,
                apiKey = apiKey,
                format = format,
                showLocalTime = showLocalTime,
            )
            if (response.isSuccessful) {
                response.body()?.let {
                    return ResultState.Success(it)
                }
            }
            ResultState.Error("${response.code()} ${response.message()}", response.code())
        } catch (e: Exception) {
            ResultState.Error(e.message.toString())
        }

    }
}