package com.hashim.weatherapplication.favourite.data.db.di

import com.hashim.weatherapplication.favourite.data.db.WeatherDatabaseRepository
import com.hashim.weatherapplication.favourite.data.db.WeatherDatabaseRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DatabaseRepositoryModule {

    @Binds
    abstract fun bindWeatherRepository(impl: WeatherDatabaseRepositoryImpl): WeatherDatabaseRepository
}