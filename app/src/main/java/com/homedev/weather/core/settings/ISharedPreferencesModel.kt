package com.homedev.weather.settings

/**
 * Created by Alexandr Zheleznyakov on 2019-10-24.
 */
interface ISharedPreferencesModel {
    companion object {
        const val LastCity = "lastCity"
        const val Humidity = "humidity"
        const val WindSpeed = "windSpeed"
        const val Pressure = "pressure"
    }

    fun clear()

    fun setLastCity(city: String)
    fun getLastCity(): String?
    fun setHumidity(value: Boolean)
    fun getHumidity(): Boolean
    fun setWindSpeed(value: Boolean)
    fun getWindSpeed(): Boolean
    fun setPressure(value: Boolean)
    fun getPressure(): Boolean
}