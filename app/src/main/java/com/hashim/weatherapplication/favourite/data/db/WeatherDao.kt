package com.hashim.weatherapplication.favourite.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weatherentity")
    suspend fun getAll(): List<WeatherEntity>

    @Insert
    suspend fun insertAll(vararg weatherEntity: WeatherEntity)

    @Delete
    suspend fun delete(weatherEntity: WeatherEntity)
}