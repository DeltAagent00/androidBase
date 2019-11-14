package com.homedev.weather.services

import android.app.IntentService
import android.content.Intent
import androidx.core.util.isEmpty
import androidx.core.util.isNotEmpty
import com.google.gson.Gson
import com.homedev.weather.api.ApiWeather
import com.homedev.weather.api.network.Result
import com.homedev.weather.api.network.repositories.WeatherRepository
import com.homedev.weather.api.response.WeatherResponse
import com.homedev.weather.core.Constants
import com.homedev.weather.core.db.DBHelper
import com.homedev.weather.core.db.WeatherTable
import com.homedev.weather.core.model.WeatherDataLoader
import com.homedev.weather.utils.LoggerUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Alexandr Zheleznyakov on 2019-10-31.
 */
class WeatherService: IntentService("myService") {
    companion object {
        const val MIN_TIME_FOR_GET_FRESH_DATA = 60 * 60 * 1000L
    }

    private val parentJob = Job()
    private val coroutineContext = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            if (it.hasExtra(Constants.INTENT_TOWN)) {
                val town = it.getStringExtra(Constants.INTENT_TOWN)

                if (checkDataInBD(town)) {
                    return
                }

                scope.launch {
                    val response = WeatherRepository.instance
                        .getWeatherForecast(town)

                    when(response) {
                        is Result.Success -> {
                            saveDataTODB(town, response.data)
                            sendResponse(response.data, ResponseCode.Success)
                        }
                        is Result.Error -> {
                            LoggerUtils.error(this, "getWeatherForecast error = " +
                                    response.exception.message)
                            sendResponse(null, ResponseCode.Error)
                        }
                    }

                }
            }
        }
    }

    private fun checkDataInBD(town: String): Boolean {
        val dbHelper = DBHelper(baseContext).readableDatabase
        val dataFromDB = WeatherTable.getAllDataFromCity(town, dbHelper)

        val currentDate = System.currentTimeMillis()

        if (dataFromDB.isNotEmpty() &&
            (currentDate - dataFromDB.keyAt(0)) < MIN_TIME_FOR_GET_FRESH_DATA) {

            val data = Gson().fromJson(dataFromDB.valueAt(0), WeatherResponse::class.java)
            sendResponse(data, ResponseCode.Success)

            return true
        } else {
            return false
        }
    }

    private fun saveDataTODB(town: String, data: WeatherResponse) {
        val dbHelper = DBHelper(baseContext).writableDatabase
        val dataFromDB = WeatherTable.getAllDataFromCity(town, dbHelper)
        val dataToString = Gson().toJson(data, WeatherResponse::class.java)

        if (dataFromDB.isEmpty()) {
            WeatherTable.addData(town, dataToString, dbHelper)
        } else {
            WeatherTable.editData(town, dataToString, dbHelper)
        }
    }

    private fun sendResponse(data: WeatherResponse? = null, responseCode: ResponseCode) {
        val sendIntent = Intent(Constants.BROADCAST_RESPONSE_WEATHER)

        if (responseCode == ResponseCode.Success) {
            sendIntent.putExtra(Constants.INTENT_RESPONSE, data)
            sendIntent.putExtra(Constants.INTENT_CODE, ResponseCode.Success)
        } else {
            sendIntent.putExtra(Constants.INTENT_CODE, ResponseCode.Error)
        }
        sendBroadcast(sendIntent)
    }
}