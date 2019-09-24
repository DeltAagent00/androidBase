package com.homedev.weather.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import com.homedev.weather.R
import com.homedev.weather.core.Constants
import com.homedev.weather.core.Constants.SAVED_REQUEST_VALUES
import com.homedev.weather.core.model.RequestModel

/**
 * Created by Alexandr Zheleznyakov on 2019-09-23.
 */
class ResultRequestActivity : BaseActivityAbs() {
    companion object {
        fun show(context: Context, requestModel: RequestModel) {
            val intent = Intent(context, ResultRequestActivity::class.java)
            intent.putExtra(Constants.INTENT_REQUEST_MODEL, requestModel)
            context.startActivity(intent)
        }
    }

    @BindView(R.id.root)
    lateinit var rootView: ViewGroup
    @BindView(R.id.town)
    lateinit var townView: View
    private var townTitleView: TextView? = null
    private var townValueView: TextView? = null

    @BindView(R.id.humidity)
    lateinit var humidityView: View
    private var humidityTitleView: TextView? = null
    private var humidityValueView: TextView? = null

    @BindView(R.id.wind_speed)
    lateinit var winSpeedView: View
    private var windSpeedTitleView: TextView? = null
    private var windSpeedValueView: TextView? = null

    @BindView(R.id.pressure)
    lateinit var pressureView: View
    private var pressureTitleView: TextView? = null
    private var pressureValueView: TextView? = null

    private var requestModel: RequestModel? = null


    /** override **/


    override fun getLayout(): Int {
        return R.layout.result_request_activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        parseIntent()
        parseSaved(savedInstanceState)

        if (isValidRequest()) {
            fillViews()
        } else {
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        requestModel?.let {
            outState.putSerializable(SAVED_REQUEST_VALUES, requestModel)
        }

        super.onSaveInstanceState(outState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    /** private **/


    private fun initView() {
        townTitleView = townView.findViewById(R.id.title)
        townValueView = townView.findViewById(R.id.value)

        humidityTitleView = humidityView.findViewById(R.id.title)
        humidityValueView = humidityView.findViewById(R.id.value)

        windSpeedTitleView = winSpeedView.findViewById(R.id.title)
        windSpeedValueView = winSpeedView.findViewById(R.id.value)

        pressureTitleView = pressureView.findViewById(R.id.title)
        pressureValueView = pressureView.findViewById(R.id.value)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun parseIntent() {
        if (intent.hasExtra(Constants.INTENT_REQUEST_MODEL)) {
            requestModel =
                intent.getSerializableExtra(Constants.INTENT_REQUEST_MODEL) as RequestModel
            intent.removeExtra(Constants.INTENT_REQUEST_MODEL)
        }
    }

    private fun parseSaved(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            if (it.containsKey(SAVED_REQUEST_VALUES)) {
                requestModel = it.get(SAVED_REQUEST_VALUES) as RequestModel
                it.remove(SAVED_REQUEST_VALUES)
            }
        }
    }

    private fun isValidRequest(): Boolean {
        return requestModel != null
    }

    private fun fillViews() {
        requestModel?.let {
            title = it.town
            townTitleView?.setText(R.string.temperature)
            townValueView?.setText(R.string.temperature_value)

            if (it.isShowHumidity) {
                humidityTitleView?.setText(R.string.humidity)
                humidityValueView?.setText(R.string.humidity_value)
            } else {
                rootView.removeView(humidityView)
            }

            if (it.isShowWindSpeed) {
                windSpeedTitleView?.setText(R.string.wind_speed)
                windSpeedValueView?.setText(R.string.wind_speed_value)
            } else {
                rootView.removeView(winSpeedView)
            }

            if (it.isShowPressure) {
                pressureTitleView?.setText(R.string.pressure)
                pressureValueView?.setText(R.string.pressure_value)
            } else {
                rootView.removeView(pressureView)
            }
        }
    }
}