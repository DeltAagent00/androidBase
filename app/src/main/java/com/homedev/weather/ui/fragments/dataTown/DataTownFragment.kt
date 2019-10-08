package com.homedev.weather.ui.fragments.dataTown

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.homedev.weather.R
import com.homedev.weather.core.Constants
import com.homedev.weather.core.IObserver
import com.homedev.weather.core.Publisher
import com.homedev.weather.core.model.RequestModel
import com.homedev.weather.core.model.WeatherViewModel
import com.homedev.weather.ui.fragments.BaseFragmentAbs
import com.homedev.weather.helper.PaddingDecoration
import kotlinx.android.synthetic.main.result_request_fragment.*
import kotlin.collections.ArrayList
import kotlin.random.Random

/**
 * Created by Alexandr Zheleznyakov on 2019-10-07.
 */
class DataTownFragment : BaseFragmentAbs(), IObserver {
    companion object {
        fun create(requestModel: RequestModel): Fragment {
            val fragment = DataTownFragment()
            val args = Bundle()
            args.putSerializable(Constants.INTENT_REQUEST_MODEL, requestModel)
            fragment.arguments = args

            return fragment
        }
    }

    @BindView(R.id.root)
    lateinit var rootView: ViewGroup

    private var requestModel: RequestModel? = null
    private var recyclerViewAdapter: DataTownAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.result_request_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseIntent()
        initView()
        initRecyclerView()
        parseSaved(savedInstanceState)

        if (isValidRequest()) {
            fillViews()
        } else {
            recyclerViewAdapter?.reset()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        requestModel?.let {
            outState.putSerializable(Constants.SAVED_REQUEST_VALUES, requestModel)
        }

        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        Publisher.instance.subscribe(this)
    }

    override fun onStop() {
        super.onStop()
        Publisher.instance.unsubscribe(this)
    }

    override fun updateData(model: RequestModel) {
        requestModel = model
        fillViews()
    }

    /** private **/


    private fun initView() {
    }

    private fun initRecyclerView() {
        val recyclerViewLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        if (recyclerViewAdapter == null) {
            recyclerViewAdapter = DataTownAdapter()
        }

        recyclerView.isSaveEnabled = true
        recyclerView.layoutManager = recyclerViewLayoutManager
        recyclerView.adapter = recyclerViewAdapter

        recyclerView.addItemDecoration(object : PaddingDecoration(
            0,
            requireContext().resources.getDimension(R.dimen.recycler_item_decorator_padding_top).toInt(),
            0,
            0
        ) {
            override fun childAtPositionNeedsPadding(position: Int, count: Int): Boolean {
                return position == 0
            }
        })
    }

    private fun parseIntent() {
        arguments?.let {
            if (it.containsKey(Constants.INTENT_REQUEST_MODEL)) {
                requestModel =
                    it.getSerializable(Constants.INTENT_REQUEST_MODEL) as RequestModel
                it.remove(Constants.INTENT_REQUEST_MODEL)
            }
        }
    }

    private fun parseSaved(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            if (it.containsKey(Constants.SAVED_REQUEST_VALUES)) {
                requestModel = it.get(Constants.SAVED_REQUEST_VALUES) as RequestModel
                it.remove(Constants.SAVED_REQUEST_VALUES)
            }
        }
    }

    private fun isValidRequest(): Boolean {
        return requestModel != null
    }

    private fun fillViews() {
        requestModel?.let {
            activity?.title = it.town
            recyclerViewAdapter?.setData(generateData(), it)
        }
    }

    private fun generateData(): Collection<WeatherViewModel> {
        val listData: ArrayList<WeatherViewModel> = ArrayList()

        for (i in 0..10) {
            listData.add(generateModel())
        }

        return listData
    }

    private fun generateModel(): WeatherViewModel {
        return WeatherViewModel(
            Random.nextInt(17, 40),
            Random.nextInt(0, 100),
            Random.nextInt(0, 50),
            Random.nextInt(690, 800)
        )
    }
}