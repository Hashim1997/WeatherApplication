package com.hashim.weatherapplication.home.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hashim.weatherapplication.R
import com.hashim.weatherapplication.home.data.models.WeatherResponse


@Composable
fun DailyForecastScreen(response: WeatherResponse) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        val dailyForecast = response.data?.weather.orEmpty()
        items(dailyForecast){
            DailyForecastCard(it)
        }
    }
}

@Composable
fun DailyForecastCard(weather: WeatherResponse.Data.Weather?) {
    Card(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.teal_700)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
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
            Box(modifier = Modifier.weight(1f, fill = true), contentAlignment = Alignment.Center) {
                Text(
                    text = "${weather?.avgtempC}°",
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
                Image(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(id = R.drawable.cloudy),
                    contentDescription = "Image",
                )
            }
        }
    }
}