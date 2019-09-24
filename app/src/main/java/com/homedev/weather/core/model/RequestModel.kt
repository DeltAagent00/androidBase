package com.homedev.weather.core.model

import java.io.Serializable

/**
 * Created by Alexandr Zheleznyakov on 2019-09-23.
 */
data class RequestModel(
    val town: String,           // город
    val isShowHumidity: Boolean,  // влажность
    val isShowWindSpeed: Boolean, // скорость ветра
    val isShowPressure: Boolean   // давление
) : Serializable