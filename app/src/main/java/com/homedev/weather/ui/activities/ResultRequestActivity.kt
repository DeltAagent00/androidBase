package com.homedev.weather.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.homedev.weather.R
import com.homedev.weather.core.Constants
import com.homedev.weather.core.model.RequestModel
import com.homedev.weather.ui.fragments.dataTown.DataTownFragmentImpl

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

    private var requestModel: RequestModel? = null


    /** override **/


    override fun getLayout(): Int {
        return R.layout.activity_request
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parseIntent()
        initView()
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

        requestModel?.let {
            title = it.town
            val fragment = DataTownFragmentImpl.create(it)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.containerView, fragment)
                .commitAllowingStateLoss()
        }
    }

    private fun parseIntent() {
        if (intent.hasExtra(Constants.INTENT_REQUEST_MODEL)) {
            requestModel =
                intent.getSerializableExtra(Constants.INTENT_REQUEST_MODEL) as RequestModel
            intent.removeExtra(Constants.INTENT_REQUEST_MODEL)
        }
    }
}