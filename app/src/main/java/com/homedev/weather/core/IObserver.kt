package com.homedev.weather.core

import com.homedev.weather.core.model.RequestModel

interface IObserver {
    fun updateData(model: RequestModel)
}
