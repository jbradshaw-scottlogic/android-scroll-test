package com.example.jack.infintescrolltest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LoadingViewHolder(view: View): RecyclerView.ViewHolder(view) {

    //private val progressBar: TextView = view.findViewById(R.id.progressBar)

    companion object {
        fun create(parent: ViewGroup): LoadingViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.loading, parent, false)
            return LoadingViewHolder(view)
        }
    }
}