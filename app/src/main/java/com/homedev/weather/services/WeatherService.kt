package com.homedev.weather.services

import android.app.IntentService
import android.content.Intent
import com.homedev.weather.api.ApiWeather
import com.homedev.weather.api.network.Result
import com.homedev.weather.api.network.repositories.WeatherRepository
import com.homedev.weather.core.Constants
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
    private val parentJob = Job()
    private val coroutineContext = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            if (it.hasExtra(Constants.INTENT_TOWN)) {
                val town = it.getStringExtra(Constants.INTENT_TOWN)

                scope.launch {
                    val response = WeatherRepository.instance
                        .getWeatherForecast(town)

                    val sendIntent = Intent(Constants.BROADCAST_RESPONSE_WEATHER)

                    when(response) {
                        is Result.Success -> {
                            sendIntent.putExtra(Constants.INTENT_RESPONSE, response.data)
                            sendIntent.putExtra(Constants.INTENT_CODE, ResponseCode.Success)
                        }
                        is Result.Error -> {
                            LoggerUtils.error(this, "getWeatherForecast error = " +
                                    response.exception.message)
                            sendIntent.putExtra(Constants.INTENT_CODE, ResponseCode.Error)
                        }
                    }
                    sendBroadcast(sendIntent)
                }
            }
        }
    }
}