package com.homedev.weather.ui.selectTown.dataTown

import android.os.Handler
import com.homedev.weather.R
import com.homedev.weather.api.Item
import com.homedev.weather.core.model.RequestModel
import com.homedev.weather.core.model.WeatherDataLoader
import com.homedev.weather.core.model.WeatherViewModel

/**
 * Created by Alexandr Zheleznyakov on 2019-10-15.
 */
class DataTownPresenterImpl(private val view: IDataTownView):
    IDataTownPresenter {
    private var requestModel: RequestModel? = null
    private val handler = Handler()

    override fun startLoadData(requestModel: RequestModel) {
        this.requestModel = requestModel
        view.resetAdapter()
        getDataFromServer()
    }

    private fun getDataFromServer() {
        requestModel?.let {
            view.enabledProgress(true)
            Thread {
                val response = WeatherDataLoader.getJSONData(it.town)
                if (response == null) {
                    handler.post {
                        view.showError(R.string.place_not_found)
                        view.enabledProgress(false)
                    }
                } else {
                    val list = response.list.map {
                        createViewModel(it)
                    }

                    handler.post {
                        view.setDataToAdapter(list)
                        view.enabledProgress(false)
                    }
                }
            }.start()
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