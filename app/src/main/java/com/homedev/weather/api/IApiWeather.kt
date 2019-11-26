package com.homedev.weather.api

import com.homedev.weather.api.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Alexandr Zheleznyakov on 2019-11-08.
 */
interface IApiWeather {
    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
        const val OPEN_WEATHER_API_KEY = "f3f2763fe63803beef4851d6365c83bc"
        const val WEATHER_HEADER_ACCESS = "x-api-key"
    }

    @GET("data/2.5/forecast")
    suspend fun getForecastWeather(@Query("q") city: String,
                                   @Query("units") units: String): WeatherResponse

    @GET("data/2.5/forecast")
    suspend fun getWeatherFromLocation(@Query("lat") lat: Double,
                                       @Query("lon") lon: Double,
                                       @Query("units") units: String): WeatherResponse
}