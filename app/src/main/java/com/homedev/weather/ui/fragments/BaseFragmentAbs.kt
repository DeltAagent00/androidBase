package com.homedev.weather.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder
import com.homedev.weather.utils.LoggerUtils

/**
 * Created by Alexandr Zheleznyakov on 2019-10-07.
 */
abstract class BaseFragmentAbs: Fragment() {
    private var unbinder: Unbinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        unbinder = ButterKnife.bind(this, view)

        val msg = "onViewCreated Fragment"
        LoggerUtils.info(this, msg)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder?.unbind()

        val msg = "onDestroyView Fragment"
        LoggerUtils.info(this, msg)
    }

    override fun onStart() {
        super.onStart()

        val msg = "onStart Fragment"
        LoggerUtils.info(this, msg)
    }

    override fun onResume() {
        super.onResume()

        val msg = "onResume Fragment"
        LoggerUtils.info(this, msg)
    }

    override fun onPause() {
        super.onPause()

        val msg = "onPause Fragment"
        LoggerUtils.info(this, msg)
    }

    override fun onStop() {
        super.onStop()

        val msg = "onStop Fragment"
        LoggerUtils.info(this, msg)
    }

    fun setTitle(@StringRes title: Int) {
        activity?.actionBar?.apply {
            setTitle(title)
        }
    }
}