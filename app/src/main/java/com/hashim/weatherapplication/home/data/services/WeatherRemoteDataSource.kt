package com.hashim.weatherapplication.home.data.services

import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(
    private val weatherService: WeatherService,
) {
}