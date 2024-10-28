package com.hashim.weatherapplication.home.ui.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hashim.weatherapplication.favourite.ui.viewmodels.FavouriteViewModel
import com.hashim.weatherapplication.search.ui.views.SearchLocationScreen
import com.hashim.weatherapplication.favourite.ui.views.FavouritesWeatherScreen
import com.hashim.weatherapplication.home.data.models.BottomNavigationItem
import com.hashim.weatherapplication.home.data.models.Screens
import com.hashim.weatherapplication.home.ui.viewmodels.HomeViewModel
import com.hashim.weatherapplication.search.ui.viewmodels.SearchViewModel


@Composable
fun BottomNavigationBar(
    homeViewModel: HomeViewModel = viewModel(),
    searchViewModel: SearchViewModel = viewModel(),
    favouritesViewModel: FavouriteViewModel = viewModel(),
) {
//initializing the default selected item
    var navigationSelectedItem by remember { mutableIntStateOf(0) }

    /**
     * by using the rememberNavController()
     * we can get the instance of the navController
     */
    val navController = rememberNavController()

//scaffold to hold our bottom navigation Bar
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentColor = Color.Transparent,
        containerColor = Color.Transparent,
        bottomBar = {
            NavigationBar {
                //getting the list of bottom navigation items for our data class
                BottomNavigationItem().bottomNavigationItems()
                    .forEachIndexed { index, navigationItem ->

                        //iterating all items with their respective indexes
                        NavigationBarItem(
                            selected = index == navigationSelectedItem,
                            label = {
                                Text(navigationItem.label)
                            },
                            icon = {
                                Icon(
                                    navigationItem.icon,
                                    contentDescription = navigationItem.label
                                )
                            },
                            onClick = {
                                navigationSelectedItem = index
                                navController.navigate(navigationItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
            }
        }
    ) { paddingValues ->
        //We need to setup our NavHost in here
        NavHost(
            navController = navController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(paddingValues = paddingValues),
        ) {
            composable(Screens.Home.route) {
                HomeScreen(homeViewModel)
            }
            composable(Screens.Search.route) {
                SearchLocationScreen(searchViewModel)
            }
            composable(Screens.Profile.route) {
                FavouritesWeatherScreen(favouritesViewModel)
            }
        }
    }
}
