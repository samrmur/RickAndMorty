package com.saam.rickandmorty.util.views

import androidx.recyclerview.widget.RecyclerView
import android.graphics.drawable.Drawable
import android.content.Context
import android.graphics.Canvas

class CustomDividerDecoration constructor(
    context: Context?,
    private val start_margin: Int,
    private val end_margin: Int
): RecyclerView.ItemDecoration() {
    private val attrs = intArrayOf(android.R.attr.listDivider)
    private val divider: Drawable?

    init {
        val styledAttributes = context?.obtainStyledAttributes(attrs)
        divider = styledAttributes?.getDrawable(0)
        styledAttributes?.recycle()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft + start_margin
        val right = parent.width - parent.paddingRight - end_margin

        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin

            divider?.let {
                val bottom = top + divider.intrinsicHeight

                divider.setBounds(left, top, right, bottom)
                divider.draw(c)
            }
        }
    }
}