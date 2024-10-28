package com.hashim.weatherapplication.home.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hashim.weatherapplication.core.constant.Network
import com.hashim.weatherapplication.core.network.ResultState
import com.hashim.weatherapplication.home.data.models.WeatherResponse
import com.hashim.weatherapplication.home.domain.GetCityWeatherDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCityWeatherDetailsUseCase: GetCityWeatherDetailsUseCase,
) : ViewModel() {

    private val _weatherState = MutableStateFlow<ResultState<WeatherResponse>>(ResultState.Idle)
    val weatherState = _weatherState.asStateFlow()

    init {
        getWeatherByCity("Amman")
    }

    fun getWeatherByCity(cityName: String) {
        viewModelScope.launch {
            _weatherState.emit(ResultState.Loading)
            _weatherState.emit(getCityWeatherDetailsUseCase.invoke(Network.API_KEY, cityName))
        }
    }

    fun getWeatherByLocation(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val location = "$latitude,$longitude"
            _weatherState.emit(ResultState.Loading)
            _weatherState.emit(getCityWeatherDetailsUseCase.invoke(Network.API_KEY, location))
        }
    }
}