package com.hashim.weatherapplication.home.domain

import com.hashim.weatherapplication.core.constant.Network
import com.hashim.weatherapplication.core.network.ResultState
import com.hashim.weatherapplication.home.data.models.WeatherResponse
import com.hashim.weatherapplication.home.data.services.WeatherRepository
import javax.inject.Inject

class GetCityWeatherDetailsUseCase @Inject constructor(
    private val repository: WeatherRepository,
) {

    suspend operator fun invoke(
        apiKey: String,
        cityName: String,
    ): ResultState<WeatherResponse> {
        return repository.getWeatherDetails(
            apiKey = apiKey,
            cityName = cityName,
            format = Network.RESPONSE_FORMAT,
            showLocalTime = "yes",
        )
    }
}