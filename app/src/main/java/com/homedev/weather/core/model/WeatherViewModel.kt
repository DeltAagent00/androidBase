package com.homedev.weather.core.model

/**
 * Created by Alexandr Zheleznyakov on 2019-10-08.
 */
data class WeatherViewModel(val temperature: Double,
                            val humidity: Int,
                            val windSpeed: Double,
                            val pressure: Double,
                            val dateText: String,
                            val iconId: String? = null)