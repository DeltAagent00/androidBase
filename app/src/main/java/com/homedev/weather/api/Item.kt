package com.homedev.weather.api

import java.io.Serializable

data class Item(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val sys: Sys,
    val weather: List<Weather>,
    val wind: Wind
): Serializable