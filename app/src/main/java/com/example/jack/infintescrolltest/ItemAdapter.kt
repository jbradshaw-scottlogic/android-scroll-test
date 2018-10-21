package com.example.jack.infintescrolltest

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter : PagedListAdapter<Item, RecyclerView.ViewHolder>(ItemComparator()) {

    var isLoadingBefore: Boolean = false
        set(value) {
            notifyRowChange(field, value, 0)
            field = value
        }

    var isLoadingAfter: Boolean = false
        set(value) {
            notifyRowChange(field, value, itemCount - 1)
            field = value
        }

    private val ITEM_VIEW_TYPE = 1
    private val LOADING_VIEW_TYPE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ITEM_VIEW_TYPE -> ItemViewHolder.create(parent)
            LOADING_VIEW_TYPE -> LoadingViewHolder.create(parent)
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)) {
            ITEM_VIEW_TYPE -> (holder as ItemViewHolder).bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(true) {
            isLoadingBefore && position == 0 -> LOADING_VIEW_TYPE
            isLoadingAfter && position == itemCount - 1 -> LOADING_VIEW_TYPE
            else -> ITEM_VIEW_TYPE
        }
    }

    override fun getItemCount(): Int {
        return (
            super.getItemCount()
            + if (isLoadingBefore) 1 else 0
            + if (isLoadingAfter) 1 else 0
        )
    }

    private fun notifyRowChange(oldValue: Boolean, newValue: Boolean, position: Int) {
        if (oldValue != newValue) {
            if (newValue) {
                notifyItemInserted(position)
            } else {
                notifyItemRemoved(position)
            }
        }
    }

    class ItemComparator: DiffUtil.ItemCallback<Item>() {
        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
            oldItem == newItem

        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
            oldItem.name == newItem.name
    }
}