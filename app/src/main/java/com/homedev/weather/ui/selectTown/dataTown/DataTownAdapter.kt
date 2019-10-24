package com.homedev.weather.ui.selectTown.dataTown

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.homedev.weather.R
import com.homedev.weather.core.model.RequestModel
import com.homedev.weather.core.model.WeatherViewModel
import com.homedev.weather.utils.ViewsUtil

/**
 * Created by Alexandr Zheleznyakov on 2019-10-08.
 */
class DataTownAdapter: RecyclerView.Adapter<DataTownAdapterHolder>() {
    private val mItems: ArrayList<WeatherViewModel> = ArrayList()
    private var requestModel: RequestModel? = null

    fun setData(itemList: Collection<WeatherViewModel>, requestModel: RequestModel) {
        mItems.clear()
        mItems.addAll(itemList)

        setRequestModel(requestModel)
    }

    fun setRequestModel(requestModel: RequestModel) {
        this.requestModel = requestModel
        notifyDataSetChanged()
    }

    fun reset() {
        mItems.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataTownAdapterHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_weather_card,
            parent, false)
        return DataTownAdapterHolder(view)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: DataTownAdapterHolder, position: Int) {
        val item = getItemByPosition(position)
        holder.fill(item, requestModel)
    }

    private fun getItemByPosition(position: Int): WeatherViewModel {
        return mItems[position]
    }
}

class DataTownAdapterHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    @BindView(R.id.titleView)
    lateinit var titleView: TextView
    @BindView(R.id.temperature)
    lateinit var temperatureView: View
    private val temperatureTitleView: TextView
    private val temperatureValueView: TextView
    @BindView(R.id.humidity)
    lateinit var humidityView: View
    private val humidityTitleView: TextView
    private val humidityValueView: TextView
    @BindView(R.id.windSpeed)
    lateinit var windSpeedView: View
    private val windSpeedTitleView: TextView
    private val windSpeedValueView: TextView
    @BindView(R.id.pressure)
    lateinit var pressureView: View
    private val pressureTitleView: TextView
    private val pressureValueView: TextView

    init {
        ButterKnife.bind(this, itemView)

        temperatureTitleView = temperatureView.findViewById(R.id.title)
        temperatureValueView = temperatureView.findViewById(R.id.value)

        humidityTitleView = humidityView.findViewById(R.id.title)
        humidityValueView = humidityView.findViewById(R.id.value)

        windSpeedTitleView = windSpeedView.findViewById(R.id.title)
        windSpeedValueView = windSpeedView.findViewById(R.id.value)

        pressureTitleView = pressureView.findViewById(R.id.title)
        pressureValueView = pressureView.findViewById(R.id.value)
    }

    private fun reset() {
        ViewsUtil.clearTextViews(titleView, temperatureTitleView, temperatureValueView,
            humidityTitleView, humidityValueView, windSpeedTitleView, windSpeedValueView,
            pressureTitleView, pressureValueView)
    }

    fun fill(item: WeatherViewModel, requestModel: RequestModel?) {
        reset()

        titleView.text = item.dateText
        setTemperature(item.temperature)
        setHumidity(item.humidity, requestModel?.isShowHumidity ?: true)
        setWindSpeed(item.windSpeed, requestModel?.isShowWindSpeed ?: true)
        setPressure(item.pressure, requestModel?.isShowPressure ?: true)
    }

    private fun setTemperature(temperatureValue: Double) {
        temperatureTitleView.setText(R.string.temperature)
        temperatureValueView.text = temperatureValueView.context.getString(R.string.temperature_value, temperatureValue)
    }

    private fun setHumidity(humidityValue: Int, isShow: Boolean) {
        if (isShow) {
            humidityTitleView.setText(R.string.humidity)
            humidityValueView.text = humidityValueView.context.getString(R.string.humidity_value, humidityValue)
            ViewsUtil.showViews(humidityView)
        } else {
            ViewsUtil.goneViews(humidityView)
        }
    }

    private fun setWindSpeed(windSpeedValue: Double, isShow: Boolean) {
        if (isShow) {
            windSpeedTitleView.setText(R.string.wind_speed)
            windSpeedValueView.text = windSpeedValueView.context.getString(R.string.wind_speed_value, windSpeedValue)
            ViewsUtil.showViews(windSpeedView)
        } else {
            ViewsUtil.goneViews(windSpeedView)
        }
    }

    private fun setPressure(pressureValue: Double, isShow: Boolean) {
        if (isShow) {
            pressureTitleView.setText(R.string.pressure)
            pressureValueView.text = pressureValueView.context.getString(R.string.pressure_value, pressureValue)
            ViewsUtil.showViews(pressureView)
        } else {
            ViewsUtil.goneViews(pressureView)
        }
    }
}