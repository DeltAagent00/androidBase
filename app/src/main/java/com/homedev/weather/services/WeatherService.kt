package com.homedev.weather.services

import android.app.IntentService
import android.content.Intent
import com.homedev.weather.core.Constants
import com.homedev.weather.core.model.WeatherDataLoader

/**
 * Created by Alexandr Zheleznyakov on 2019-10-31.
 */
class WeatherService: IntentService("myService") {
    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            if (it.hasExtra(Constants.INTENT_TOWN)) {
                val town = it.getStringExtra(Constants.INTENT_TOWN)
                val response = WeatherDataLoader.getJSONData(town)
                val code = if (response == null) {
                    ResponseCode.Error
                } else {
                    ResponseCode.Success
                }

                val sendIntent = Intent(Constants.BROADCAST_RESPONSE_WEATHER)
                sendIntent.putExtra(Constants.INTENT_RESPONSE, response)
                sendIntent.putExtra(Constants.INTENT_CODE, code)

                sendBroadcast(sendIntent)
            }
        }
    }
}