package com.homedev.weather.ui.selectTown.dataTown

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.homedev.weather.core.settings.SharedPreferencesModelImpl
import com.homedev.weather.ui.fragments.BaseFragmentAbs
import com.homedev.weather.helper.PaddingDecoration
import com.homedev.weather.settings.ISharedPreferencesModel
import com.homedev.weather.utils.ViewsUtil
import kotlinx.android.synthetic.main.result_request_fragment.*

/**
 * Created by Alexandr Zheleznyakov on 2019-10-07.
 */
class DataTownFragmentImpl : BaseFragmentAbs(),
    IDataTownView, IObserver {
    companion object {
        fun create(requestModel: RequestModel): Fragment {
            val fragment = DataTownFragmentImpl()
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

    private var sharedPreferencesModel: ISharedPreferencesModel? = null
    private val presenter: IDataTownPresenter

    init {
        presenter = DataTownPresenterImpl(this)
    }

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
            resetAdapter()
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

    override fun resetAdapter() {
        recyclerViewAdapter?.reset()
    }

    override fun setDataToAdapter(list: Collection<WeatherViewModel>) {
        requestModel?.let {
            recyclerViewAdapter?.setData(list, it)
        }
    }

    override fun showError(resString: Int) {
        context?.let {
            Toast.makeText(it, resString, Toast.LENGTH_SHORT).show()
        }
    }

    override fun enabledProgress(enabled: Boolean) {
        if (enabled) {
            ViewsUtil.showViews(progressView)
        } else {
            ViewsUtil.goneViews(progressView)
        }
    }


    /** private **/


    private fun initView() {
        context?.let {
            sharedPreferencesModel = SharedPreferencesModelImpl(it)
            requestModel?.let { itModel ->
                itModel.isShowHumidity = sharedPreferencesModel?.getHumidity() ?: false
                itModel.isShowWindSpeed = sharedPreferencesModel?.getWindSpeed() ?: false
                itModel.isShowPressure = sharedPreferencesModel?.getPressure() ?: false
            }
        }
        enabledProgress(false)
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
            presenter.startLoadData(it)
        }
    }
}