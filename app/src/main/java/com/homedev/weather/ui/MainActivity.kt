package com.homedev.weather.ui

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.*
import butterknife.BindView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputLayout
import com.homedev.weather.R
import com.homedev.weather.core.Constants.SAVED_REQUEST_VALUES
import com.homedev.weather.core.model.RequestModel
import com.homedev.weather.helper.SimpleTextWatcher
import com.homedev.weather.utils.LoggerUtils
import com.homedev.weather.utils.ViewsUtil

class MainActivity : BaseActivityAbs() {
    @BindView(R.id.root)
    lateinit var rootView: View
    @BindView(R.id.tllTown)
    lateinit var ttlTownView: TextInputLayout
    @BindView(R.id.edit_text_town)
    lateinit var editTextTownView: EditText

    @BindView(R.id.humidity)
    lateinit var humidityView: View
    private var humidityTitle: TextView? = null
    private var humiditySwitch: SwitchMaterial? = null

    @BindView(R.id.wind_speed)
    lateinit var windSpeedView: View
    private var windSpeedTitle: TextView? = null
    private var windSpeedSwitch: SwitchMaterial? = null

    @BindView(R.id.pressure)
    lateinit var pressureView: View
    private var pressureTitle: TextView? = null
    private var pressureSwitch: SwitchMaterial? = null

    @BindView(R.id.request_button)
    lateinit var requestButtonView: Button


    /** override **/


    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val msg = "onCreate"
        showMessage(msg)
        LoggerUtils.info(this, msg)

        initView()
        parseSaved(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val request = createModelRequest()
        outState.putSerializable(SAVED_REQUEST_VALUES, request)

        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()

        val msg = "onStart"
        showMessage(msg)
        LoggerUtils.info(this, msg)
    }

    override fun onResume() {
        super.onResume()

        val msg = "onResume"
        showMessage(msg)
        LoggerUtils.info(this, msg)
    }

    override fun onRestart() {
        super.onRestart()

        val msg = "onRestart"
        showMessage(msg)
        LoggerUtils.info(this, msg)
    }

    override fun onPause() {
        super.onPause()

        val msg = "onPause"
        showMessage(msg)
        LoggerUtils.info(this, msg)
    }

    override fun onStop() {
        super.onStop()

        val msg = "onStop"
        showMessage(msg)
        LoggerUtils.info(this, msg)
    }

    override fun onDestroy() {
        super.onDestroy()

        val msg = "onDestroy"
        showMessage(msg)
        LoggerUtils.info(this, msg)
    }


    /** private **/


    private fun initView() {
        setTitle(R.string.title)

        humidityTitle = humidityView.findViewById(R.id.title)
        humiditySwitch = humidityView.findViewById(R.id.switcher)

        windSpeedTitle = windSpeedView.findViewById(R.id.title)
        windSpeedSwitch = windSpeedView.findViewById(R.id.switcher)

        pressureTitle = pressureView.findViewById(R.id.title)
        pressureSwitch = pressureView.findViewById(R.id.switcher)

        editTextTownView.addTextChangedListener(object : SimpleTextWatcher {
            override fun afterTextChanged(s: Editable) {
                enableTownError(false)
            }
        })

        humidityTitle?.setText(R.string.show_humidity)
        windSpeedTitle?.setText(R.string.show_wind_speed)
        pressureTitle?.setText(R.string.show_pressure)
        requestButtonView.setText(R.string.button_text)

        requestButtonView.setOnClickListener {
            onClickButton()
        }

        rootView.setOnTouchListener { v, _ ->
            ViewsUtil.hideSoftKeyboard(v)
            true
        }
    }

    private fun createModelRequest(): RequestModel {
        val town = editTextTownView.text.toString()
        val humidity = humiditySwitch?.isChecked ?: false
        val windSpeed = windSpeedSwitch?.isChecked ?: false
        val pressure = pressureSwitch?.isChecked ?: false
        return RequestModel(town, humidity, windSpeed, pressure)
    }

    private fun parseSaved(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            if (it.containsKey(SAVED_REQUEST_VALUES)) {
                val requestValues = it.get(SAVED_REQUEST_VALUES) as RequestModel
                it.remove(SAVED_REQUEST_VALUES)
                editTextTownView.setText(requestValues.town)
                humiditySwitch?.isChecked = requestValues.isShowHumidity
                windSpeedSwitch?.isChecked = requestValues.isShowWindSpeed
                pressureSwitch?.isChecked = requestValues.isShowPressure
            }
        }
    }

    private fun checkAndShowErrorInputTown(): Boolean {
        val isValid = editTextTownView.text.toString().trim().isNotEmpty()
        enableTownError(!isValid)
        return isValid
    }

    private fun enableTownError(enabled: Boolean) {
        if (enabled) {
            ttlTownView.error = getString(R.string.error_can_not_be_empty)
        } else {
            ttlTownView.isErrorEnabled = false
        }
    }

    private fun onClickButton() {
        if (checkAndShowErrorInputTown()) {
            val requestModel = createModelRequest()
            ResultRequestActivity.show(this, requestModel)
        }
    }
}
