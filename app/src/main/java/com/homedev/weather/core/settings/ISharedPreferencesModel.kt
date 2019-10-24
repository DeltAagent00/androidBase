package com.homedev.weather.settings

/**
 * Created by Alexandr Zheleznyakov on 2019-10-24.
 */
interface ISharedPreferencesModel {
    companion object {
        const val Humidity = "humidity"
        const val WindSpeed = "windSpeed"
        const val Pressure = "pressure"
    }

    fun clear()

    fun setHumidity(value: Boolean)
    fun getHumidity(): Boolean
    fun setWindSpeed(value: Boolean)
    fun getWindSpeed(): Boolean
    fun setPressure(value: Boolean)
    fun getPressure(): Boolean

}