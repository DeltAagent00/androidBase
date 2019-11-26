package com.homedev.weather.api.network.repositories

import android.location.Location
import com.homedev.weather.api.ApiWeather
import com.homedev.weather.api.network.Result
import com.homedev.weather.api.response.WeatherResponse

/**
 * Created by Alexandr Zheleznyakov on 2019-11-08.
 */
class WeatherRepository: BaseRepository() {
    companion object {
        val instance = WeatherRepository()
    }

    private val apiWeather = ApiWeather.instance

    suspend fun getWeatherForecast(town: String): Result<WeatherResponse> {
        return safeApiCall(call = {
            apiWeather.api.getForecastWeather(town, "metric")
        })
    }

    suspend fun getWeatherFromLocation(location: Location): Result<WeatherResponse> {
        return safeApiCall(call = {
            apiWeather.api.getWeatherFromLocation(location.latitude, location.longitude, "metric")
        })
    }
}