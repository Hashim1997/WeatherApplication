package com.hashim.weatherapplication.core.network

import com.hashim.weatherapplication.home.data.services.DefaultWeatherRepository
import com.hashim.weatherapplication.home.data.services.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesProvider {

    @Binds
    abstract fun bindWeatherRepository(defaultWeatherRepository: DefaultWeatherRepository): WeatherRepository
}