package com.homedev.weather.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.homedev.weather.R
import com.homedev.weather.core.model.RequestModel
import com.homedev.weather.core.settings.SharedPreferencesModelImpl
import com.homedev.weather.ui.fragments.BaseFragmentAbs
import com.homedev.weather.ui.resultRequest.ResultRequestActivity
import com.homedev.weather.ui.selectTown.SelectTownFragment
import com.homedev.weather.ui.selectTown.dataTown.DataTownFragmentImpl
import com.homedev.weather.utils.ViewsUtil

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
    }

    private fun initView() {
        setTitle(R.string.title)

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
}