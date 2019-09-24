package com.homedev.weather.helper

import android.text.Editable
import android.text.TextWatcher

/**
 * Created by Alexandr Zheleznyakov on 2019-09-24.
 */
interface SimpleTextWatcher : TextWatcher {
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable) {
    }
}