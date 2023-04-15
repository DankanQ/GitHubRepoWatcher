package com.example.githubrepowatcher.presentation.repo

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepowatcher.R

class ItemDecoration(
    private val context: Context
) : RecyclerView.ItemDecoration() {
    private val spaceSize = context.resources.getDimensionPixelSize(
        R.dimen.space_size
    )

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        val itemPosition = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount
        with(outRect) {
            when (itemPosition) {
                0 -> {
                    top = spaceSize + context.resources.getDimensionPixelSize(
                        R.dimen.top_margin_first_item
                    )
                    bottom = spaceSize
                }
                itemCount - 1 -> {
                    top = spaceSize + context.resources.getDimensionPixelSize(
                        R.dimen.top_margin_other_items
                    )
                    bottom = spaceSize - context.resources.getDimensionPixelSize(
                        R.dimen.bottom_margin_last_item
                    )
                }
                else -> {
                    top = spaceSize + context.resources.getDimensionPixelSize(
                        R.dimen.top_margin_other_items
                    )
                    bottom = spaceSize
                }
            }
        }
    }
}