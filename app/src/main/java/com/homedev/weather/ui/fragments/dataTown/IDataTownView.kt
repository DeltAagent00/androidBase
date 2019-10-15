package com.homedev.weather.ui.fragments.dataTown

import androidx.annotation.StringRes
import com.homedev.weather.core.model.WeatherViewModel

/**
 * Created by Alexandr Zheleznyakov on 2019-10-15.
 */
interface IDataTownView {
    fun resetAdapter()
    fun setDataToAdapter(list: Collection<WeatherViewModel>)
    fun showError(@StringRes resString: Int)
    fun enabledProgress(enabled: Boolean)
}