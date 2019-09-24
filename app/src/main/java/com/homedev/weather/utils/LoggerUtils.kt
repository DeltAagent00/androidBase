package com.homedev.weather.utils

import android.util.Log

/**
 * Created by Alexandr Zheleznyakov on 2019-09-24.
 */
object LoggerUtils {
    fun info(obj: Any, msg: String) {
        Log.i(obj.toString(), msg)
    }

    fun debug(obj: Any, msg: String) {
        Log.d(obj.toString(), msg)
    }

    fun error(obj: Any, msg: String) {
        Log.e(obj.toString(), msg)
    }
}