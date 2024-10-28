package com.hashim.weatherapplication.core.constant

import java.util.concurrent.TimeUnit

object Network {
    const val BASE_URL = "https://api.worldweatheronline.com/premium/v1/"
    const val WEATHER = "weather.ashx"

    const val TIMEOUT_VALUE = 90L
    val TIMEOUT_UNIT = TimeUnit.SECONDS

    const val RESPONSE_FORMAT = "json"

    const val API_KEY = "76532ba6142a46d49d9171622242510"
}