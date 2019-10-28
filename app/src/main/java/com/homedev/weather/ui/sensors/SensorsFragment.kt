package com.homedev.weather.ui.sensors

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.homedev.weather.R
import kotlinx.android.synthetic.main.fragment_sensors.*

/**
 * Created by Alexandr Zheleznyakov on 2019-10-28.
 */
class SensorsFragment: Fragment() {
    private var sensorManager: SensorManager? = null
    private var sensorTemperature: Sensor? = null
    private var sensorHumidity: Sensor? = null
    private var sensorsListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }

        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                when (it.sensor.type) {
                    Sensor.TYPE_AMBIENT_TEMPERATURE -> {
                        showTemperature(it)
                    }
                    Sensor.TYPE_RELATIVE_HUMIDITY -> {
                        showHumidity(it)
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_sensors, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initSensors()
    }

    override fun onStart() {
        super.onStart()
        subscribeListener()
    }

    override fun onStop() {
        super.onStop()
        unSubscribeListener()
    }

    /** private **/


    private fun initView() {

    }

    private fun initSensors() {
        sensorManager = context?.getSystemService(SENSOR_SERVICE) as SensorManager
        sensorTemperature = sensorManager?.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        sensorHumidity = sensorManager?.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)

        if (sensorTemperature == null) {
            showMessage(R.string.temperature_no_isset)
        }
        if (sensorHumidity == null) {
            showMessage(R.string.humidity_no_isset)
        }
    }

    private fun subscribeListener() {
        sensorTemperature?.let {
            sensorManager?.registerListener(sensorsListener, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        sensorHumidity?.let {
            sensorManager?.registerListener(sensorsListener, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    private fun unSubscribeListener() {
        sensorTemperature?.let {
            sensorManager?.unregisterListener(sensorsListener, it)
        }
        sensorHumidity?.let {
            sensorManager?.unregisterListener(sensorsListener, it)
        }
    }

    private fun showMessage(@StringRes message: Int) {
        context?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showTemperature(event: SensorEvent) {
        sensor1View.text = getString(R.string.temperature) + " " + getString(R.string.temperature_value, event.values[0])
    }

    private fun showHumidity(event: SensorEvent) {
        sensor2View.text =  getString(R.string.humidity) + " " + getString(R.string.humidity_value, event.values[0].toInt())
    }
}