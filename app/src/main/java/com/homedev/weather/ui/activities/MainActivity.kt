package com.homedev.weather.ui.activities

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.homedev.weather.R
import com.homedev.weather.helper.OnOneClickListener
import com.homedev.weather.ui.fragments.dataTown.DataTownFragment
import com.homedev.weather.ui.fragments.SelectTownFragment
import com.homedev.weather.utils.ViewsUtil

class MainActivity : BaseActivityAbs() {

    private var lastClickTimeAbout = 0L

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_about, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about -> {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastClickTimeAbout > OnOneClickListener.MIN_CLICK_INTERVAL_MSEC) {
                    lastClickTimeAbout = currentTime
                    showAbout()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    //endregion


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

    private fun showAbout() {
        Toast.makeText(this, R.string.about_text, Toast.LENGTH_SHORT).show()
    }
}
