package com.homedev.weather.core.model

import com.google.gson.Gson
import com.homedev.weather.api.response.WeatherResponse
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by Alexandr Zheleznyakov on 2019-10-15.
 */
object WeatherDataLoader {
    private const val OPEN_WEATHER_API_KEY = "f3f2763fe63803beef4851d6365c83bc"
    private const val OPEN_WEATHER_API_URL =
        "https://api.openweathermap.org/data/2.5/forecast?q=%s&units=metric"
    private const val KEY = "x-api-key"
    private const val ALL_GOOD = 200

    fun getJSONData(city: String): WeatherResponse? {
        try {
            val url = URL(String.format(OPEN_WEATHER_API_URL, city))
            val connection = url.openConnection() as HttpURLConnection
            connection.addRequestProperty(KEY, OPEN_WEATHER_API_KEY)

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val rawData = StringBuilder(1024)
            var tempVariable: String? = reader.readLine()

            while (tempVariable != null) {
                rawData.append(tempVariable).append("\n")
                tempVariable = reader.readLine()
            }

            reader.close()

            return if (connection.responseCode == ALL_GOOD) {
                Gson().fromJson(rawData.toString(), WeatherResponse::class.java)
            } else {
                null
            }
        } catch (exc: Exception) {
            exc.printStackTrace()
            return null
        }

    }
}