package com.homedev.weather.ui.main

import android.content.res.Configuration
import android.os.Bundle
import androidx.navigation.findNavController
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.homedev.weather.R
import com.homedev.weather.helper.OnOneClickListener
import com.homedev.weather.ui.activities.BaseActivityAbs
import com.homedev.weather.ui.settings.SettingsActivity

class MainActivity : BaseActivityAbs() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var lastClickTimeSettings = 0L

    /** override **/


    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()
        initNavMenu()
        initView()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        setContentView( R.layout.activity_main)
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastClickTimeSettings > OnOneClickListener.MIN_CLICK_INTERVAL_MSEC) {
                    lastClickTimeSettings = currentTime
                    showSettings()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    //endregion

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    /** private **/


    private fun initToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun initNavMenu() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_sensors,
                R.id.nav_send,
                R.id.nav_about
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun initView() {
    }

    private fun showSettings() {
        SettingsActivity.show(this)
    }
}
