package com.homedev.weather.core.settings

import android.content.Context
import android.content.SharedPreferences
import com.homedev.weather.core.Constants
import com.homedev.weather.settings.ISharedPreferencesModel

/**
 * Created by Alexandr Zheleznyakov on 2019-10-24.
 */
class SharedPreferencesModelImpl(private val context: Context): ISharedPreferencesModel {
    private val preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    /** private **/

    private fun save(key: String, value: String?) {
        preferences.edit()
            .putString(key, value)
            .apply()
    }

    private fun save(key: String, value: Boolean) {
        preferences.edit()
            .putBoolean(key, value)
            .apply()
    }

    private fun save(key: String, value: Int) {
        preferences.edit()
            .putInt(key, value)
            .apply()
    }

    private fun save(key: String, value: Long) {
        preferences.edit()
            .putLong(key, value)
            .apply()
    }

    private fun save(key: String, value: Float) {
        preferences.edit()
            .putFloat(key, value)
            .apply()
    }

    private fun getString(key: String): String? {
        return getString(key, null)
    }

    private fun getString(key: String, defaultValue: String?): String? {
        return preferences.getString(key, defaultValue)
    }

    private fun getBool(key: String): Boolean {
        return getBool(key, false)
    }

    private fun getBool(key: String, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    private fun getInt(key: String): Int {
        return getInt(key, 0)
    }

    private fun getInt(key: String, defaultValue: Int): Int {
        return preferences.getInt(key, defaultValue)
    }

    private fun getLong(key: String): Long {
        return getLong(key, 0)
    }

    private fun getLong(key: String, defaultValue: Long): Long {
        return preferences.getLong(key, defaultValue)
    }

    private fun getFloat(key: String): Float {
        return getFloat(key, 0f)
    }

    private fun getFloat(key: String, defaultValue: Float): Float {
        return preferences.getFloat(key, defaultValue)
    }


    /** override **/


    override fun clear() {
        preferences.edit()
            .clear()
            .apply()
    }

    override fun setLastCity(city: String) {
        save(ISharedPreferencesModel.LastCity, city)
    }

    override fun getLastCity(): String? {
        return getString(ISharedPreferencesModel.LastCity)
    }

    override fun setHumidity(value: Boolean) {
        save(ISharedPreferencesModel.Humidity, value)
    }

    override fun getHumidity(): Boolean {
        return getBool(ISharedPreferencesModel.Humidity)
    }

    override fun setWindSpeed(value: Boolean) {
        save(ISharedPreferencesModel.WindSpeed, value)
    }

    override fun getWindSpeed(): Boolean {
        return getBool(ISharedPreferencesModel.WindSpeed)
    }

    override fun setPressure(value: Boolean) {
        save(ISharedPreferencesModel.Pressure, value)
    }

    override fun getPressure(): Boolean {
        return getBool(ISharedPreferencesModel.Pressure)
    }

}