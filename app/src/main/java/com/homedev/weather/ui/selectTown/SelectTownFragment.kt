package com.homedev.weather.ui.selectTown

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.ListView
import butterknife.BindView
import com.google.android.material.snackbar.Snackbar
import com.homedev.weather.R
import com.homedev.weather.core.Constants
import com.homedev.weather.core.Publisher
import com.homedev.weather.core.model.RequestModel
import com.homedev.weather.ui.fragments.BaseFragmentAbs
import com.homedev.weather.ui.resultRequest.ResultRequestActivity
import com.homedev.weather.utils.ViewsUtil
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Created by Alexandr Zheleznyakov on 2019-10-07.
 */
class SelectTownFragment: BaseFragmentAbs() {
    @BindView(R.id.root)
    lateinit var rootView: View

    private var currentPosition = 0
    private var isExistDataScreen = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initList()
        parseSaved(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        isExistDataScreen =
            resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("CurrentCity", 0)
        }

        if (isExistDataScreen) {
            townListView.choiceMode = ListView.CHOICE_MODE_SINGLE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(Constants.SAVED_TOWN_POSITION, currentPosition)

        super.onSaveInstanceState(outState)
    }



    private fun initViews() {
        rootView.setOnTouchListener { v, _ ->
            ViewsUtil.hideSoftKeyboard(v)
            true
        }
    }

    private fun initList() {
        context?.let {
            val adapter = ArrayAdapter.createFromResource(
                it, R.array.town_list, android.R.layout.simple_list_item_activated_1 )
            townListView.adapter = adapter

            townListView.setOnItemClickListener { _, _, position, _ ->
                currentPosition = position
                showData()
            }
        }
    }

    private fun showData() {
        if (isExistDataScreen) {
            townListView.setItemChecked(currentPosition, true)
            Publisher.instance.notify(createModelRequest())
        } else {
            view?.let {
                val confirmText = getString(R.string.confirm_text, getTownBySelectedPosition())
                Snackbar.make(it, confirmText, Snackbar.LENGTH_LONG)
                    .setAction(R.string.yes) { showScreenResultRequest() }
                    .show()
            }
        }
    }

    private fun getTownBySelectedPosition(): String {
        return townListView.adapter.getItem(currentPosition) as String
    }

    private fun createModelRequest(): RequestModel {
        val town = getTownBySelectedPosition()
        return RequestModel(town)
    }

    private fun parseSaved(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            if (it.containsKey(Constants.SAVED_REQUEST_VALUES)) {
                val townPosition = it.getInt(Constants.SAVED_TOWN_POSITION)
                it.remove(Constants.SAVED_TOWN_POSITION)

                townListView.setItemChecked(townPosition, true)
            }
        }
    }

    private fun showScreenResultRequest() {
        context?.let { itContext ->
            ResultRequestActivity.show(itContext, createModelRequest())
        }
    }
}