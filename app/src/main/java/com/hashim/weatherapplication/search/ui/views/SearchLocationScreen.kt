package com.hashim.weatherapplication.search.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hashim.weatherapplication.core.network.ResultState
import com.hashim.weatherapplication.favourite.data.db.toFavouriteModel
import com.hashim.weatherapplication.favourite.ui.views.FavouriteWeatherItem
import com.hashim.weatherapplication.home.data.models.WeatherResponse
import com.hashim.weatherapplication.home.ui.views.ShowLoading
import com.hashim.weatherapplication.search.ui.viewmodels.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SearchLocationScreen(
    searchViewModel: SearchViewModel = viewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.STARTED -> {
                searchViewModel.clearSearch()
            }

            else -> Unit
        }
    }

    val searchText by searchViewModel.searchText.collectAsState()
    val isSearching by searchViewModel.isSearching.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black,
    ) {
        Column {
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                inputField = {
                    SearchBarDefaults.InputField(
                        query = searchText,
                        onQueryChange = searchViewModel::onSearchTextChange,
                        onSearch = searchViewModel::onSearch,
                        expanded = isSearching,
                        onExpandedChange = { searchViewModel.onToggleSearch() },
                        placeholder = { Text("Search for country or city") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    )
                },
                expanded = isSearching,
                onExpandedChange = { searchViewModel.onToggleSearch() },
            ) {}
            Box(
                modifier = Modifier
                    .weight(1f, true)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                val weather = searchViewModel.weatherState.collectAsState()
                when (weather.value) {
                    is ResultState.Error -> Unit
                    ResultState.Idle -> {
                        Text(
                            text = "Search for a location",
                            fontSize = 20.sp,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp),
                        )
                    }

                    ResultState.Loading -> {
                        ShowLoading()
                    }

                    is ResultState.Success -> {
                        val data =
                            (weather.value as ResultState.Success<WeatherResponse>).data.toFavouriteModel()
                        var isChecked = searchViewModel.isChecked.collectAsState()
                        //TODO fix issue with duplicated item
                        FavouriteWeatherItem(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            response = data,
                            isCheck = isChecked.value,
                            onCheckedChange = {
                                searchViewModel.isChecked(it)
                                if (it) {
                                    searchViewModel.insertFavourite(data)
                                } else {
                                    searchViewModel.deleteFavourite(data)
                                }
                            }
                        )
                    }
                }
            }
        }
    }

}