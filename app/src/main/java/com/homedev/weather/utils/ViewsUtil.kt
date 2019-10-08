package com.homedev.weather.utils

import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView

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

    fun isLandscape(context: Context): Boolean {
        return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    fun isPortrait(context: Context): Boolean {
        return context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

    fun goneViews(vararg views: View?) {
        for (v in views) {
            if (v?.visibility != View.GONE) {
                v?.visibility = View.GONE
            }
        }
    }

    fun hideViews(vararg views: View?) {
        for (v in views) {
            if (v?.visibility != View.INVISIBLE) {
                v?.visibility = View.INVISIBLE
            }
        }
    }

    fun showViews(vararg views: View?) {
        for (v in views) {
            if (v?.visibility != View.VISIBLE) {
                v?.visibility = View.VISIBLE
            }
        }
    }

    fun clearTextViews(vararg textViews: TextView?) {
        for (tv in textViews) {
            tv?.text = null
        }
    }
}