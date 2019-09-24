package com.homedev.weather.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by Alexandr Zheleznyakov on 2019-09-24.
 */
object ViewsUtil {
    fun hideSoftKeyboard(view: View?) {
        view?.let {
            val inputMethodManager =
                it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}