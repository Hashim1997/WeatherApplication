package com.hashim.weatherapplication.home.data.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class WeatherResponse(
    @SerializedName("data")
    val `data`: Data?
) : Parcelable {
    @Keep
    @Parcelize
    data class Data(
        @SerializedName("ClimateAverages")
        val climateAverages: List<ClimateAverage?>?,
        @SerializedName("current_condition")
        val currentCondition: List<CurrentCondition?>?,
        @SerializedName("request")
        val request: List<Request?>?,
        @SerializedName("weather")
        val weather: List<Weather?>?
    ) : Parcelable {
        @Keep
        @Parcelize
        data class ClimateAverage(
            @SerializedName("month")
            val month: List<Month?>?
        ) : Parcelable {
            @Keep
            @Parcelize
            data class Month(
                @SerializedName("absMaxTemp")
                val absMaxTemp: String?,
                @SerializedName("absMaxTemp_F")
                val absMaxTempF: String?,
                @SerializedName("avgDailyRainfall")
                val avgDailyRainfall: String?,
                @SerializedName("avgMinTemp")
                val avgMinTemp: String?,
                @SerializedName("avgMinTemp_F")
                val avgMinTempF: String?,
                @SerializedName("index")
                val index: String?,
                @SerializedName("name")
                val name: String?
            ) : Parcelable
        }

        @Keep
        @Parcelize
        data class CurrentCondition(
            @SerializedName("cloudcover")
            val cloudcover: String?,
            @SerializedName("FeelsLikeC")
            val feelsLikeC: String?,
            @SerializedName("FeelsLikeF")
            val feelsLikeF: String?,
            @SerializedName("humidity")
            val humidity: String?,
            @SerializedName("observation_time")
            val observationTime: String?,
            @SerializedName("precipInches")
            val precipInches: String?,
            @SerializedName("precipMM")
            val precipMM: String?,
            @SerializedName("pressure")
            val pressure: String?,
            @SerializedName("pressureInches")
            val pressureInches: String?,
            @SerializedName("temp_C")
            val tempC: String?,
            @SerializedName("temp_F")
            val tempF: String?,
            @SerializedName("uvIndex")
            val uvIndex: String?,
            @SerializedName("visibility")
            val visibility: String?,
            @SerializedName("visibilityMiles")
            val visibilityMiles: String?,
            @SerializedName("weatherCode")
            val weatherCode: String?,
            @SerializedName("weatherDesc")
            val weatherDesc: List<WeatherDesc?>?,
            @SerializedName("weatherIconUrl")
            val weatherIconUrl: List<WeatherIconUrl?>?,
            @SerializedName("winddir16Point")
            val winddir16Point: String?,
            @SerializedName("winddirDegree")
            val winddirDegree: String?,
            @SerializedName("windspeedKmph")
            val windspeedKmph: String?,
            @SerializedName("windspeedMiles")
            val windspeedMiles: String?
        ) : Parcelable {
            @Keep
            @Parcelize
            data class WeatherDesc(
                @SerializedName("value")
                val value: String?
            ) : Parcelable

            @Keep
            @Parcelize
            data class WeatherIconUrl(
                @SerializedName("value")
                val value: String?
            ) : Parcelable
        }

        @Keep
        @Parcelize
        data class Request(
            @SerializedName("query")
            val query: String?,
            @SerializedName("type")
            val type: String?
        ) : Parcelable

        @Keep
        @Parcelize
        data class Weather(
            @SerializedName("astronomy")
            val astronomy: List<Astronomy?>?,
            @SerializedName("avgtempC")
            val avgtempC: String?,
            @SerializedName("avgtempF")
            val avgtempF: String?,
            @SerializedName("date")
            val date: String?,
            @SerializedName("hourly")
            val hourly: List<Hourly?>?,
            @SerializedName("maxtempC")
            val maxtempC: String?,
            @SerializedName("maxtempF")
            val maxtempF: String?,
            @SerializedName("mintempC")
            val mintempC: String?,
            @SerializedName("mintempF")
            val mintempF: String?,
            @SerializedName("sunHour")
            val sunHour: String?,
            @SerializedName("totalSnow_cm")
            val totalSnowCm: String?,
            @SerializedName("uvIndex")
            val uvIndex: String?
        ) : Parcelable {
            @Keep
            @Parcelize
            data class Astronomy(
                @SerializedName("moon_illumination")
                val moonIllumination: String?,
                @SerializedName("moon_phase")
                val moonPhase: String?,
                @SerializedName("moonrise")
                val moonrise: String?,
                @SerializedName("moonset")
                val moonset: String?,
                @SerializedName("sunrise")
                val sunrise: String?,
                @SerializedName("sunset")
                val sunset: String?
            ) : Parcelable

            @Keep
            @Parcelize
            data class Hourly(
                @SerializedName("chanceoffog")
                val chanceoffog: String?,
                @SerializedName("chanceoffrost")
                val chanceoffrost: String?,
                @SerializedName("chanceofhightemp")
                val chanceofhightemp: String?,
                @SerializedName("chanceofovercast")
                val chanceofovercast: String?,
                @SerializedName("chanceofrain")
                val chanceofrain: String?,
                @SerializedName("chanceofremdry")
                val chanceofremdry: String?,
                @SerializedName("chanceofsnow")
                val chanceofsnow: String?,
                @SerializedName("chanceofsunshine")
                val chanceofsunshine: String?,
                @SerializedName("chanceofthunder")
                val chanceofthunder: String?,
                @SerializedName("chanceofwindy")
                val chanceofwindy: String?,
                @SerializedName("cloudcover")
                val cloudcover: String?,
                @SerializedName("DewPointC")
                val dewPointC: String?,
                @SerializedName("DewPointF")
                val dewPointF: String?,
                @SerializedName("diffRad")
                val diffRad: String?,
                @SerializedName("FeelsLikeC")
                val feelsLikeC: String?,
                @SerializedName("FeelsLikeF")
                val feelsLikeF: String?,
                @SerializedName("HeatIndexC")
                val heatIndexC: String?,
                @SerializedName("HeatIndexF")
                val heatIndexF: String?,
                @SerializedName("humidity")
                val humidity: String?,
                @SerializedName("precipInches")
                val precipInches: String?,
                @SerializedName("precipMM")
                val precipMM: String?,
                @SerializedName("pressure")
                val pressure: String?,
                @SerializedName("pressureInches")
                val pressureInches: String?,
                @SerializedName("shortRad")
                val shortRad: String?,
                @SerializedName("tempC")
                val tempC: String?,
                @SerializedName("tempF")
                val tempF: String?,
                @SerializedName("time")
                val time: String?,
                @SerializedName("uvIndex")
                val uvIndex: String?,
                @SerializedName("visibility")
                val visibility: String?,
                @SerializedName("visibilityMiles")
                val visibilityMiles: String?,
                @SerializedName("weatherCode")
                val weatherCode: String?,
                @SerializedName("weatherDesc")
                val weatherDesc: List<WeatherDesc?>?,
                @SerializedName("weatherIconUrl")
                val weatherIconUrl: List<WeatherIconUrl?>?,
                @SerializedName("WindChillC")
                val windChillC: String?,
                @SerializedName("WindChillF")
                val windChillF: String?,
                @SerializedName("WindGustKmph")
                val windGustKmph: String?,
                @SerializedName("WindGustMiles")
                val windGustMiles: String?,
                @SerializedName("winddir16Point")
                val winddir16Point: String?,
                @SerializedName("winddirDegree")
                val winddirDegree: String?,
                @SerializedName("windspeedKmph")
                val windspeedKmph: String?,
                @SerializedName("windspeedMiles")
                val windspeedMiles: String?
            ) : Parcelable {
                @Keep
                @Parcelize
                data class WeatherDesc(
                    @SerializedName("value")
                    val value: String?
                ) : Parcelable

                @Keep
                @Parcelize
                data class WeatherIconUrl(
                    @SerializedName("value")
                    val value: String?
                ) : Parcelable
            }
        }
    }
}