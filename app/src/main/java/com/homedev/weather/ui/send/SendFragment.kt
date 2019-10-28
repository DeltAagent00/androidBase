package com.homedev.weather.ui.send

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.homedev.weather.R
import kotlinx.android.synthetic.main.fragment_send.*

class SendFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_send, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    /** private **/


    private fun initView() {
        sendBtnView.setOnClickListener {
            context?.let { itContext ->
                Toast.makeText(itContext, R.string.message_success_send, Toast.LENGTH_SHORT).show()
            }
        }
    }
}