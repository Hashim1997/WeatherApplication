package com.hashim.weatherapplication.search.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hashim.weatherapplication.core.constant.Network
import com.hashim.weatherapplication.core.network.ResultState
import com.hashim.weatherapplication.favourite.data.db.WeatherEntity
import com.hashim.weatherapplication.home.data.models.WeatherResponse
import com.hashim.weatherapplication.home.domain.GetCityWeatherDetailsUseCase
import com.hashim.weatherapplication.search.domain.DeleteWeatherUseCase
import com.hashim.weatherapplication.search.domain.InsertWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchCityWeatherUseCase: GetCityWeatherDetailsUseCase,
    private val insertWeatherUseCase: InsertWeatherUseCase,
    private val deleteWeatherUseCase: DeleteWeatherUseCase,
) : ViewModel() {
    //first state whether the search is happening or not
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _isChecked = MutableStateFlow(false)
    val isChecked = _isChecked.asStateFlow()

    //second state the text typed by the user
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _weatherState = MutableStateFlow<ResultState<WeatherResponse>>(ResultState.Idle)
    val weatherState = _weatherState.asStateFlow()

    private fun getWeatherByCity(cityName: String) {
        viewModelScope.launch {
            _weatherState.emit(ResultState.Loading)
            _weatherState.emit(searchCityWeatherUseCase.invoke(Network.API_KEY, cityName))
        }
    }

    fun isChecked(isChecked: Boolean){
        viewModelScope.launch{
            _isChecked.emit(isChecked)
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onSearch(text: String) {
        _isSearching.value = false
        getWeatherByCity(text)
    }

    fun onToggleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChange("")
        }
    }

    fun insertFavourite(weatherEntity: WeatherEntity) {
        viewModelScope.launch {
            insertWeatherUseCase.invoke(weatherEntity)
        }
    }

    fun deleteFavourite(weatherEntity: WeatherEntity) {
        viewModelScope.launch {
            deleteWeatherUseCase.invoke(weatherEntity)
        }
    }

    fun clearSearch() {
        viewModelScope.launch{
            _weatherState.emit(ResultState.Idle)
            _isSearching.emit(false)
            _searchText.emit("")
            _isChecked.emit(false)
        }
    }
}