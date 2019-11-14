package com.homedev.weather.api.response

import java.io.Serializable

data class Wind(
    val deg: Double,
    val speed: Double
): Serializable