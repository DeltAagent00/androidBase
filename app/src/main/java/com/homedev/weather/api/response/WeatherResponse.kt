package com.homedev.weather.api.response

import java.io.Serializable

data class WeatherResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Item>,
    val message: Any
): Serializable