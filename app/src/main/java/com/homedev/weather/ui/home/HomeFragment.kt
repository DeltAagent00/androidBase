package com.homedev.weather.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.isEmpty
import com.google.gson.Gson
import com.homedev.weather.R
import com.homedev.weather.api.network.Result
import com.homedev.weather.api.network.repositories.WeatherRepository
import com.homedev.weather.api.response.WeatherResponse
import com.homedev.weather.core.db.DBHelper
import com.homedev.weather.core.db.WeatherTable
import com.homedev.weather.core.model.RequestModel
import com.homedev.weather.core.settings.SharedPreferencesModelImpl
import com.homedev.weather.services.ResponseCode
import com.homedev.weather.ui.fragments.BaseFragmentAbs
import com.homedev.weather.ui.resultRequest.ResultRequestActivity
import com.homedev.weather.ui.selectTown.SelectTownFragment
import com.homedev.weather.ui.selectTown.dataTown.DataTownFragmentImpl
import com.homedev.weather.utils.LocationUtils
import com.homedev.weather.utils.LoggerUtils
import com.homedev.weather.utils.ViewsUtil
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : BaseFragmentAbs() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        if (askForPermissionsLocation()) {
            getLocationForWeather()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSIONS_REQUEST_MULTIPLE_PERMISSIONS) {
            for (i in permissions.indices) {
                if (permissions[i] == Manifest.permission.ACCESS_FINE_LOCATION ||
                    permissions[i] == Manifest.permission.ACCESS_COARSE_LOCATION) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        getLocationForWeather()
                        return
                    }
                }
            }
            showWeatherView()
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun initView() {
        setTitle(R.string.title)
    }

    private fun showWeatherView() {
        ViewsUtil.goneViews(progressView)

        val mContext = context ?: return

        val fragment = SelectTownFragment()
        val fragmentTransaction = fragmentManager?.beginTransaction()

        val startRequestModel = createRequestModel()

        if (ViewsUtil.isLandscape(mContext)) {
            val fragmentData = DataTownFragmentImpl.create(startRequestModel)
            fragmentTransaction
                ?.replace(R.id.containerViewLeft, fragment)
                ?.replace(R.id.containerViewRight, fragmentData)
        } else {
            fragmentTransaction
                ?.replace(R.id.containerView, fragment)
        }
        fragmentTransaction?.commitAllowingStateLoss()

        if (ViewsUtil.isPortrait(mContext)) {
            startRequestModel?.let {
                showScreenResultRequest(it)
            }
        }
    }

    private fun createRequestModel(): RequestModel? {
        return context?.let {
            val sharedPreferencesModel = SharedPreferencesModelImpl(it)

            sharedPreferencesModel.getLastCity()?.let { itCity ->
                RequestModel(
                    itCity,
                    sharedPreferencesModel.getHumidity(),
                    sharedPreferencesModel.getWindSpeed(),
                    sharedPreferencesModel.getPressure()
                )
            }
        }
    }

    private fun showScreenResultRequest(requestModel: RequestModel) {
        context?.let { itContext ->
            ResultRequestActivity.show(itContext, requestModel)
        }
    }

    private fun getLocationForWeather() {
        context?.let { itContext ->
            LocationUtils.startGetLastLocation(itContext) {
                scope.launch {
                    val response = WeatherRepository.instance
                        .getWeatherFromLocation(it)

                    withContext(Dispatchers.Main) {
                        when(response) {
                            is Result.Success -> {
                                saveDataTODB(response.data)

                                val sharedPreferencesModel = SharedPreferencesModelImpl(itContext)
                                sharedPreferencesModel.setLastCity(response.data.city.name)

                                showWeatherView()
                            }
                            is Result.Error -> {
                                LoggerUtils.error(this, "getWeatherFromLocation($it) error = " +
                                        response.exception.message)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun saveDataTODB(data: WeatherResponse) {
        context?.let {
            val town = data.city.name
            val dbHelper = DBHelper(it).writableDatabase
            val dataFromDB = WeatherTable.getAllDataFromCity(town, dbHelper)
            val dataToString = Gson().toJson(data, WeatherResponse::class.java)

            if (dataFromDB.isEmpty()) {
                WeatherTable.addData(town, dataToString, dbHelper)
            } else {
                WeatherTable.editData(town, dataToString, dbHelper)
            }
        }
    }
}