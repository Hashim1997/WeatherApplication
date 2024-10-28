package com.hashim.weatherapplication.home.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.hashim.weatherapplication.R
import com.hashim.weatherapplication.home.data.models.WeatherResponse

@Composable
fun CurrentWeatherScreen(response: WeatherResponse) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Filled.LocationOn,
                contentDescription = "Location",
                tint = Color.White
            )
            Text(
                text = response.data?.request?.firstOrNull()?.query.toString(),
                color = Color.White,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        //TODO convert localtime to date & day
        Text(
            text = "Tue, Mar 2",
            color = Color.Gray,
            fontSize = 11.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        AsyncImage(
            model = response.data?.currentCondition?.firstOrNull()?.weatherIconUrl?.firstOrNull()?.value,
            contentDescription = "Image",
            modifier = Modifier.size(100.dp),
            placeholder = painterResource(R.drawable.cloudy),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${response.data?.currentCondition?.firstOrNull()?.tempC}°",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            shape = RoundedCornerShape(4.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.teal_700)),
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                text = response.data?.currentCondition?.firstOrNull()?.weatherDesc?.firstOrNull()?.value.toString(),
                color = Color.White,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            VerticalText(
                title = "Wind",
                value = "${response.data?.currentCondition?.firstOrNull()?.windspeedKmph} km/h",
                modifier = Modifier.weight(1f)
            )
            VerticalDivider(
                modifier = Modifier.height(32.dp),
                thickness = 0.5.dp,
                color = Color.Gray,
            )
            VerticalText(
                title = "Humidity",
                value = "${response.data?.currentCondition?.firstOrNull()?.humidity}%",
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
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
            thickness = 0.5.dp, color = Color.Gray,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            text = "Hourly Forecast",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
        ) {
            val list = response.data?.weather?.firstOrNull()?.hourly.orEmpty()
            items(list) {
                HourlyForecastCard(it)
            }
        }
    }
}


@Composable
fun VerticalText(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = Color.White,
            )
            Text(
                text = value,
                color = Color.Gray,
            )
        }
    }
}

@Composable
fun HourlyForecastCard(hourly: WeatherResponse.Data.Weather.Hourly?) {
    Card(
        modifier = Modifier.padding(end = 8.dp),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.teal_700)),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${hourly?.tempC}°",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                model = hourly?.weatherIconUrl?.firstOrNull()?.value,
                modifier = Modifier.size(48.dp),
                contentDescription = "Image",
                placeholder = painterResource(R.drawable.cloudy)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = hourly?.time.toString(),
                color = Color.LightGray,
            )
        }
    }
}
