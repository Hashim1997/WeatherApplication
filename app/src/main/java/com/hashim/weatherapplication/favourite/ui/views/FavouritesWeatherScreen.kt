package com.hashim.weatherapplication.favourite.ui.views

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.hashim.weatherapplication.R
import com.hashim.weatherapplication.favourite.data.db.WeatherEntity
import com.hashim.weatherapplication.favourite.ui.viewmodels.FavouriteViewModel
import com.hashim.weatherapplication.favourite.ui.viewmodels.FavouritesState
import com.hashim.weatherapplication.home.ui.views.ShowLoading
import com.hashim.weatherapplication.home.ui.views.VerticalText

@Preview
@Composable
fun FavouritesWeatherScreen(
    favouritesViewModel: FavouriteViewModel = viewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.STARTED -> {
                favouritesViewModel.getAllFavourites()
            }

            else -> Unit
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        val favourites = favouritesViewModel.favouritesState.collectAsState()
        when (favourites.value) {
            FavouritesState.Empty -> {
                EmptyScreen()
            }

            is FavouritesState.Favourites -> {
                val list = (favourites.value as FavouritesState.Favourites).favourites
                LazyColumn(modifier = Modifier.padding(vertical = 16.dp)) {
                    items(list) { item ->
                        FavouriteWeatherItem(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            response = item,
                            isCheck = true,
                            onCheckedChange = {
                                if (it.not()) {
                                    favouritesViewModel.deleteFavourite(item)
                                }
                            }
                        )
                    }
                }
            }

            FavouritesState.Loading -> ShowLoading()
        }
    }
}

@Composable
fun EmptyScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "No Data Found",
            fontSize = 24.sp,
            color = Color.White,
        )
    }
}

@Composable
fun FavouriteWeatherItem(
    modifier: Modifier = Modifier,
    response: WeatherEntity,
    isCheck: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
) {
    Card(
        modifier = modifier.padding(8.dp),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.teal_700)),
    ) {
        Box {
            Checkbox(
                checked = isCheck,
                onCheckedChange = onCheckedChange,
                modifier = Modifier.align(Alignment.TopEnd),
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.White,
                    checkmarkColor = colorResource(R.color.teal_700),
                )
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.LocationOn,
                        contentDescription = "Location",
                        tint = Color.White
                    )
                    Text(
                        text = response.location,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f, fill = true)
                            .padding(start = 16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Sat",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Feb, 26",
                            color = Color.LightGray,
                            fontSize = 11.sp,
                        )
                    }
                    Box(
                        modifier = Modifier.weight(1f, fill = true),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = response.temperature,
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f, fill = true)
                            .padding(end = 16.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        AsyncImage(
                            modifier = Modifier.size(48.dp),
                            model = response.weatherIcon,
                            contentDescription = "Image",
                            placeholder = painterResource(R.drawable.cloudy),
                        )
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    thickness = 0.5.dp, color = Color.White,
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    VerticalText(
                        title = "Wind",
                        value = "${response.windSpeed} km/h",
                        modifier = Modifier.weight(1f)
                    )
                    VerticalDivider(
                        modifier = Modifier.height(32.dp),
                        thickness = 0.5.dp,
                        color = Color.Gray,
                    )
                    VerticalText(
                        title = "Humidity",
                        value = "${response.humidity}%",
                        modifier = Modifier.weight(1f)
                    )
                    VerticalDivider(
                        modifier = Modifier.height(32.dp),
                        thickness = 0.5.dp,
                        color = Color.Gray,
                    )
                    VerticalText(
                        title = "Low",
                        value = "12",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}