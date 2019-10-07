package com.homedev.weather.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import butterknife.BindView
import com.homedev.weather.R
import com.homedev.weather.core.Constants
import com.homedev.weather.core.IObserver
import com.homedev.weather.core.Publisher
import com.homedev.weather.core.model.RequestModel
import com.homedev.weather.utils.ViewsUtil

/**
 * Created by Alexandr Zheleznyakov on 2019-10-07.
 */
class DataTownFragment: BaseFragmentAbs(), IObserver {

    companion object {
        fun create(requestModel: RequestModel): Fragment {
            val fragment = DataTownFragment()
            val args = Bundle()
            args.putSerializable(Constants.INTENT_REQUEST_MODEL, requestModel)
            fragment.arguments = args

            return fragment
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
    @BindView(R.id.plateView)
    lateinit var plateView: View

    private var requestModel: RequestModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.result_request_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseIntent()
        initView()
        parseSaved(savedInstanceState)

        if (isValidRequest()) {
            fillViews()
            enabledPlateView(true)
        } else {
            enabledPlateView(false)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        requestModel?.let {
            outState.putSerializable(Constants.SAVED_REQUEST_VALUES, requestModel)
        }

        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        Publisher.instance.subscribe(this)
    }

    override fun onStop() {
        super.onStop()
        Publisher.instance.unsubscribe(this)
    }

    override fun updateData(model: RequestModel) {
        requestModel = model
        fillViews()
        enabledPlateView(true)
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
    }

    private fun parseIntent() {
        arguments?.let {
            if (it.containsKey(Constants.INTENT_REQUEST_MODEL)) {
                requestModel =
                    it.getSerializable(Constants.INTENT_REQUEST_MODEL) as RequestModel
                it.remove(Constants.INTENT_REQUEST_MODEL)
            }
        }
    }

    private fun parseSaved(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            if (it.containsKey(Constants.SAVED_REQUEST_VALUES)) {
                requestModel = it.get(Constants.SAVED_REQUEST_VALUES) as RequestModel
                it.remove(Constants.SAVED_REQUEST_VALUES)
            }
        }
    }

    private fun isValidRequest(): Boolean {
        return requestModel != null
    }

    private fun fillViews() {
        requestModel?.let {
            townTitleView?.setText(R.string.temperature)
            townValueView?.setText(R.string.temperature_value)

            if (it.isShowHumidity) {
                humidityTitleView?.setText(R.string.humidity)
                humidityValueView?.setText(R.string.humidity_value)
                ViewsUtil.showViews(humidityView)
            } else {
                ViewsUtil.goneViews(humidityView)
            }

            if (it.isShowWindSpeed) {
                windSpeedTitleView?.setText(R.string.wind_speed)
                windSpeedValueView?.setText(R.string.wind_speed_value)
                ViewsUtil.showViews(winSpeedView)
            } else {
                ViewsUtil.goneViews(winSpeedView)
            }

            if (it.isShowPressure) {
                pressureTitleView?.setText(R.string.pressure)
                pressureValueView?.setText(R.string.pressure_value)
                ViewsUtil.showViews(pressureView)
            } else {
                ViewsUtil.goneViews(pressureView)
            }
        }
    }

    private fun enabledPlateView(enable: Boolean) {
        if (enable) {
            plateView.visibility = View.VISIBLE
        } else {
            plateView.visibility = View.GONE
        }
    }
}