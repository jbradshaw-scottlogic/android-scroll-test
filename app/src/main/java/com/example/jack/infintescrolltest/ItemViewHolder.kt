package com.example.jack.infintescrolltest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val textView: TextView = view.findViewById(R.id.textView)

    fun bind(item: Item?) {
        textView.text = item?.name
    }

    companion object {
        fun create(parent: ViewGroup): ItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
            return ItemViewHolder(view)
        }
    }
}