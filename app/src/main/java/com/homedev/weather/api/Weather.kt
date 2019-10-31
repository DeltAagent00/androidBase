package com.homedev.weather.api

import java.io.Serializable

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
): Serializable