package com.homedev.weather.core

import com.homedev.weather.core.model.RequestModel

/**
 * Created by Alexandr Zheleznyakov on 2019-10-07.
 */
class Publisher {
    companion object {
        val instance = Publisher()
    }

    private val observers = ArrayList<IObserver>()   // Все обозреватели

    // Подписать
    fun subscribe(observer: IObserver) {
        observers.add(observer)
    }

    // Отписать
    fun unsubscribe(observer: IObserver) {
        observers.remove(observer)
    }

    // Разослать событие
    fun notify(model: RequestModel) {
        for (observer in observers) {
            observer.updateData(model)
        }
    }
}