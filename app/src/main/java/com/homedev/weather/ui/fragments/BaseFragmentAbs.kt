package com.homedev.weather.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder
import com.homedev.weather.utils.LoggerUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * Created by Alexandr Zheleznyakov on 2019-10-07.
 */
abstract class BaseFragmentAbs: Fragment() {
    companion object {
        const val PERMISSIONS_REQUEST_MULTIPLE_PERMISSIONS = 1000
    }

    private val parentJob = Job()
    private val coroutineContext = parentJob + Dispatchers.Default
    protected val scope = CoroutineScope(coroutineContext)
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

    fun askForPermissionsLocation(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        } else {
            val permissions = java.util.ArrayList<String>()
            if (ContextCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
            }

            if (!permissions.isEmpty()) {
                val params = permissions.toTypedArray()
                requestPermissions(params, PERMISSIONS_REQUEST_MULTIPLE_PERMISSIONS)
                return false
            }
            return true
        }
    }
}