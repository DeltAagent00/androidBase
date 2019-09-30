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
    @BindView(R.id.town_spinner)
    lateinit var townSpinnerView: Spinner

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

    private var townList: Array<String>? = null

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
        initSpinnerTown()

        humidityTitle = humidityView.findViewById(R.id.title)
        humiditySwitch = humidityView.findViewById(R.id.switcher)

        windSpeedTitle = windSpeedView.findViewById(R.id.title)
        windSpeedSwitch = windSpeedView.findViewById(R.id.switcher)

        pressureTitle = pressureView.findViewById(R.id.title)
        pressureSwitch = pressureView.findViewById(R.id.switcher)


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

    private fun initSpinnerTown() {
        townList = resources.getStringArray(R.array.town_list)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, townList)
        townSpinnerView.adapter = adapter
    }

    private fun createModelRequest(): RequestModel {
        val town = getTownBySelectedPosition(townSpinnerView.selectedItemPosition)
        val humidity = humiditySwitch?.isChecked ?: false
        val windSpeed = windSpeedSwitch?.isChecked ?: false
        val pressure = pressureSwitch?.isChecked ?: false
        return RequestModel(town, humidity, windSpeed, pressure)
    }

    private fun getTownBySelectedPosition(position: Int): String {
        return when {
            townList.isNullOrEmpty() -> return "Unknown town"
            position < 0 || position >= (townList?.size ?: 0) -> townList?.first() ?: "Unknown town"
            else -> townList?.get(position) ?: "Unknown town"
        }
    }

    private fun getTownPositionByValue(town: String): Int {
        val mTownList = townList ?: emptyArray()
        var position = 0

        for (item: String in mTownList) {
            if (item == town) {
                break
            }
            position++
        }
        return position
    }

    private fun parseSaved(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            if (it.containsKey(SAVED_REQUEST_VALUES)) {
                val requestValues = it.get(SAVED_REQUEST_VALUES) as RequestModel
                it.remove(SAVED_REQUEST_VALUES)
                getTownBySelectedPosition(getTownPositionByValue(requestValues.town))
                humiditySwitch?.isChecked = requestValues.isShowHumidity
                windSpeedSwitch?.isChecked = requestValues.isShowWindSpeed
                pressureSwitch?.isChecked = requestValues.isShowPressure
            }
        }
    }

    private fun onClickButton() {
        val requestModel = createModelRequest()
        ResultRequestActivity.show(this, requestModel)
    }
}
