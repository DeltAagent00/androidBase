package com.homedev.weather.core.model

import java.io.Serializable

/**
 * Created by Alexandr Zheleznyakov on 2019-09-23.
 */
data class RequestModel(
    val town: String,           // город
    var isShowHumidity: Boolean = false,  // влажность
    var isShowWindSpeed: Boolean = false, // скорость ветра
    var isShowPressure: Boolean = false   // давление
) : Serializable