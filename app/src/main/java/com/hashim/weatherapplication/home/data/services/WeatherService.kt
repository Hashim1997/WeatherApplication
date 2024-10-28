package com.hashim.weatherapplication.home.data.services

import com.hashim.weatherapplication.core.constant.Network
import com.hashim.weatherapplication.home.data.models.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherService {

    @GET(Network.WEATHER)
    suspend fun getCityWeatherDetails(
        @Query("q") cityName: String,
        @Query("key") apiKey: String,
        @Query("format") format: String,
        @Query("showlocaltime") showLocalTime: String,
    ): Response<WeatherResponse>
}