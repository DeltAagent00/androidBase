package com.homedev.weather.ui

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * Created by Alexandr Zheleznyakov on 2019-09-23.
 */
abstract class BaseActivityAbs : AppCompatActivity() {
    private var unBinder: Unbinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        setContentView(getLayout())
        unBinder = ButterKnife.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        unBinder?.unbind()
    }

    protected fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    @LayoutRes
    protected abstract fun getLayout(): Int
}