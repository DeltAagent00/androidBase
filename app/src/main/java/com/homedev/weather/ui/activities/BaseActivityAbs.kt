package com.homedev.weather.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import butterknife.ButterKnife
import butterknife.Unbinder
import com.homedev.weather.utils.LoggerUtils

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

        val msg = "onCreate Activity"
        LoggerUtils.info(this, msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        unBinder?.unbind()

        val msg = "onStop Activity"
        LoggerUtils.info(this, msg)
    }

    protected fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    @LayoutRes
    protected abstract fun getLayout(): Int


    override fun onStart() {
        super.onStart()

        val msg = "onStart Activity"
        LoggerUtils.info(this, msg)
    }

    override fun onResume() {
        super.onResume()

        val msg = "onResume Activity"
        LoggerUtils.info(this, msg)
    }

    override fun onRestart() {
        super.onRestart()

        val msg = "onRestart Activity"
        LoggerUtils.info(this, msg)
    }

    override fun onPause() {
        super.onPause()

        val msg = "onPause Activity"
        LoggerUtils.info(this, msg)
    }

    override fun onStop() {
        super.onStop()

        val msg = "onStop Activity"
        LoggerUtils.info(this, msg)
    }
}