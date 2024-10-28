package com.hashim.weatherapplication.core.network

import okhttp3.logging.HttpLoggingInterceptor


fun loggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}
