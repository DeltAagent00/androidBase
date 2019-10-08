package com.homedev.weather.ui.activities

import android.content.res.Configuration
import android.os.Bundle
import com.homedev.weather.R
import com.homedev.weather.ui.fragments.dataTown.DataTownFragment
import com.homedev.weather.ui.fragments.SelectTownFragment
import com.homedev.weather.utils.ViewsUtil

class MainActivity : BaseActivityAbs() {

    /** override **/


    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        setContentView( R.layout.activity_main)
        initView()
    }


    /** private **/


    private fun initView() {
        setTitle(R.string.title)

        val fragment = SelectTownFragment()

        val fragmentTransaction = supportFragmentManager
            .beginTransaction()


        if (ViewsUtil.isLandscape(baseContext)) {
            val fragmentData = DataTownFragment()
            fragmentTransaction
                .replace(R.id.containerViewLeft, fragment)
                .replace(R.id.containerViewRight, fragmentData)
        } else {
            fragmentTransaction
                .replace(R.id.containerView, fragment)
        }
        fragmentTransaction.commitAllowingStateLoss()
    }
}
