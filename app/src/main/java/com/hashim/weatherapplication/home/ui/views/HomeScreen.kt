package com.hashim.weatherapplication.home.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hashim.weatherapplication.R
import com.hashim.weatherapplication.core.network.ResultState
import com.hashim.weatherapplication.home.data.models.WeatherResponse
import com.hashim.weatherapplication.home.ui.viewmodels.HomeViewModel


@Preview
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(),
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        val weather = homeViewModel.weatherState.collectAsState()
        when (weather.value) {
            is ResultState.Error -> Unit
            ResultState.Idle -> Unit
            ResultState.Loading -> {
                ShowLoading()
            }

            is ResultState.Success -> {
                val data = (weather.value as ResultState.Success<WeatherResponse>).data
                TabScreen(data)
            }
        }
    }
}

@Composable
fun ShowLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            color = colorResource(R.color.teal_700)
        )
    }
}

@Composable
fun TabScreen(data: WeatherResponse) {
    val tabs = listOf("Today", "Daily")
    // Display the TabRowComponent with specified tabs and content screens
    TabRowComponent(
        tabs = tabs,
        contentScreens = listOf(
            { CurrentWeatherScreen(data) },  // Content screen for Tab 1
            { DailyForecastScreen(data) },      // Content screen for Tab 2
        ),
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
fun TabRowComponent(
    tabs: List<String>,
    contentScreens: List<@Composable () -> Unit>,
    modifier: Modifier = Modifier,
) {
    // State to keep track of the selected tab index
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    // Column layout to arrange tabs vertically and display content screens
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 32.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TabRow composable to display tabs
        TabRow(
            modifier = Modifier
                .width(150.dp)
                .clip(RoundedCornerShape(50.dp)),
            selectedTabIndex = selectedTabIndex,
            divider = {},
            indicator = { tabPositions ->
                // Indicator for the selected tab
                Box { }
            }
        ) {
            // Iterate through each tab title and create a tab
            tabs.forEachIndexed { index, tabTitle ->
                val selected = selectedTabIndex == index
                Tab(
                    modifier = if (selected) Modifier
                        .clip(RoundedCornerShape(50))
                        .background(colorResource(R.color.teal_700))
                    else Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Color.White),
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                ) {
                    // Text displayed on the tab
                    Text(
                        text = tabTitle,
                        modifier = Modifier.padding(8.dp),
                        color = if (selected) Color.White else Color.Black
                    )
                }
            }
        }

        // Display the content screen corresponding to the selected tab
        contentScreens.getOrNull(selectedTabIndex)?.invoke()
    }
}