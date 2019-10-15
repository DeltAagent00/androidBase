package com.homedev.weather.ui.fragments.dataTown

import com.homedev.weather.core.model.RequestModel

/**
 * Created by Alexandr Zheleznyakov on 2019-10-15.
 */
interface IDataTownPresenter {
    fun startLoadData(requestModel: RequestModel)
}