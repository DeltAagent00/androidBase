package com.homedev.weather.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.TextView
import butterknife.BindView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.homedev.weather.R
import com.homedev.weather.core.settings.SharedPreferencesModelImpl
import com.homedev.weather.settings.ISharedPreferencesModel
import com.homedev.weather.ui.activities.BaseActivityAbs

/**
 * Created by Alexandr Zheleznyakov on 2019-10-24.
 */
class SettingsActivity: BaseActivityAbs() {
    companion object {
        fun show(context: Context) {
            val intent = Intent(context, SettingsActivity::class.java)
            context.startActivity(intent)
        }
    }

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

    private val checkListener = CompoundButton.OnCheckedChangeListener { _, _ ->
        saveSettings()
    }

    private var sharedPreferencesModel: ISharedPreferencesModel? = null

    override fun getLayout(): Int {
        return R.layout.activity_settings
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferencesModel = SharedPreferencesModelImpl(this)
        initView()
        readSettings()
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setTitle(R.string.action_settings)

        humidityTitle = humidityView.findViewById(R.id.title)
        humiditySwitch = humidityView.findViewById(R.id.switcher)

        windSpeedTitle = windSpeedView.findViewById(R.id.title)
        windSpeedSwitch = windSpeedView.findViewById(R.id.switcher)

        pressureTitle = pressureView.findViewById(R.id.title)
        pressureSwitch = pressureView.findViewById(R.id.switcher)

        humiditySwitch?.setOnCheckedChangeListener(checkListener)
        windSpeedSwitch?.setOnCheckedChangeListener(checkListener)
        pressureSwitch?.setOnCheckedChangeListener(checkListener)

        humidityTitle?.setText(R.string.show_humidity)
        windSpeedTitle?.setText(R.string.show_wind_speed)
        pressureTitle?.setText(R.string.show_pressure)
    }

    private fun saveSettings() {
        humiditySwitch?.isChecked?.let {
            sharedPreferencesModel?.setHumidity(it)
        }
        windSpeedSwitch?.isChecked?.let {
            sharedPreferencesModel?.setWindSpeed(it)
        }
        pressureSwitch?.isChecked?.let {
            sharedPreferencesModel?.setPressure(it)
        }
    }

    private fun readSettings() {
        humiditySwitch?.isChecked = sharedPreferencesModel?.getHumidity() ?: false
        windSpeedSwitch?.isChecked = sharedPreferencesModel?.getWindSpeed() ?: false
        pressureSwitch?.isChecked = sharedPreferencesModel?.getPressure() ?: false
    }
}