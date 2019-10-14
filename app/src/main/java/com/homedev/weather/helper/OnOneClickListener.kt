package com.homedev.weather.helper

import android.view.View
import android.os.SystemClock


/**
 * Created by Alexandr Zheleznyakov on 2019-09-30.
 */
abstract class OnOneClickListener: View.OnClickListener {
    companion object {
        const val MIN_CLICK_INTERVAL_MSEC: Long = 1000
    }

    private var lastClickTime: Long = 0

    override fun onClick(v: View) {
        val currentTime = SystemClock.elapsedRealtime()
        if (currentTime - lastClickTime > MIN_CLICK_INTERVAL_MSEC) {
            lastClickTime = currentTime
            onOneClick(v)
        }
    }

    abstract fun onOneClick(v: View)
}