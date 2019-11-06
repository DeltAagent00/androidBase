package com.homedev.weather.ui.selectTown.dataTown

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import com.homedev.weather.R
import com.homedev.weather.api.Item
import com.homedev.weather.api.WeatherResponse
import com.homedev.weather.core.Constants
import com.homedev.weather.core.model.RequestModel
import com.homedev.weather.core.model.WeatherDataLoader
import com.homedev.weather.core.model.WeatherViewModel
import com.homedev.weather.core.settings.SharedPreferencesModelImpl
import com.homedev.weather.services.ResponseCode
import com.homedev.weather.services.WeatherService
import com.homedev.weather.settings.ISharedPreferencesModel
import com.homedev.weather.utils.LoggerUtils
import kotlin.math.E

/**
 * Created by Alexandr Zheleznyakov on 2019-10-15.
 */
class DataTownPresenterImpl(private val context: Context,
                            private val view: IDataTownView):
    IDataTownPresenter {

    private val sharedPreferencesModel: ISharedPreferencesModel
    private var requestModel: RequestModel? = null
    private val receiver: BroadcastReceiver
    private val filter = IntentFilter(Constants.BROADCAST_RESPONSE_WEATHER)

    init {
        sharedPreferencesModel = SharedPreferencesModelImpl(context)

        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.let {

                    val code = if (intent.hasExtra(Constants.INTENT_CODE)) {
                        intent.getSerializableExtra(Constants.INTENT_CODE) as ResponseCode
                    } else {
                        ResponseCode.Error
                    }

                    if (code == ResponseCode.Error) {
                        view.showError(R.string.place_not_found)
                        view.enabledProgress(false)
                        return
                    }

                    if (intent.hasExtra(Constants.INTENT_RESPONSE)) {
                        val result = intent.getSerializableExtra(Constants.INTENT_RESPONSE) as WeatherResponse

                        val list = result.list.map {
                            createViewModel(it)
                        }
                        view.setDataToAdapter(list)
                        view.enabledProgress(false)
                    }
                }
            }
        }
    }

    override fun startLoadData(requestModel: RequestModel) {
        this.requestModel = requestModel

        sharedPreferencesModel.setLastCity(requestModel.town)

        view.resetAdapter()
        getDataFromServer()
    }

    override fun onStart() {
        context.registerReceiver(receiver, filter)
    }

    override fun onStop() {
        context.unregisterReceiver(receiver)
    }

    private fun getDataFromServer() {
        requestModel?.let {
            view.enabledProgress(true)

            val intent = Intent(context, WeatherService::class.java)
            intent.putExtra(Constants.INTENT_TOWN, it.town)
            context.startService(intent)
        }
    }

    private fun createViewModel(item: Item): WeatherViewModel {
        return WeatherViewModel(
            item.main.temp,
            item.main.humidity,
            item.wind.speed,
            item.main.pressure,
            item.dt_txt
        )
    }
}