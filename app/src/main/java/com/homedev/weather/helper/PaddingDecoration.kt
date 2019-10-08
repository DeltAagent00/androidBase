package com.homedev.weather.helper

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Alexandr Zheleznyakov on 2019-10-08.
 */
abstract class PaddingDecoration(private val paddingLeft: Int,
                                 private val paddingTop: Int,
                                 private val paddingRight: Int,
                                 private val paddingBottom: Int):
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val pos = parent.getChildAdapterPosition(view)
        if (childAtPositionNeedsPadding(pos, parent.adapter?.itemCount ?: 0)) {
            outRect.set(paddingLeft, paddingTop, paddingRight, paddingBottom)
        } else {
            outRect.set(0, 0, 0, 0)
        }
    }

    abstract fun childAtPositionNeedsPadding(position: Int, count: Int): Boolean

}
