package com.hashim.weatherapplication.favourite.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hashim.weatherapplication.favourite.data.db.WeatherEntity
import com.hashim.weatherapplication.favourite.domain.GetAllWeatherUseCase
import com.hashim.weatherapplication.search.domain.DeleteWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val getAllWeatherUseCase: GetAllWeatherUseCase,
    private val deleteWeatherUseCase: DeleteWeatherUseCase,
) : ViewModel() {

    private val _favouritesState = MutableStateFlow<FavouritesState>(FavouritesState.Loading)
    val favouritesState = _favouritesState.asStateFlow()


    fun getAllFavourites() {
        viewModelScope.launch {
            _favouritesState.emit(FavouritesState.Loading)
            val data = getAllWeatherUseCase.invoke()
            if (data.isEmpty()) {
                _favouritesState.emit(FavouritesState.Empty)
            } else {
                _favouritesState.emit(FavouritesState.Favourites(data))
            }
        }
    }

    fun deleteFavourite(weatherEntity: WeatherEntity) {
        viewModelScope.launch {
            deleteWeatherUseCase.invoke(weatherEntity)
            getAllFavourites()
        }
    }
}

sealed interface FavouritesState {
    data object Loading : FavouritesState
    data object Empty : FavouritesState
    data class Favourites(val favourites: List<WeatherEntity>) : FavouritesState
}