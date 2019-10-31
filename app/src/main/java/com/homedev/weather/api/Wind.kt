package com.homedev.weather.api

import java.io.Serializable

data class Wind(
    val deg: Double,
    val speed: Double
): Serializable