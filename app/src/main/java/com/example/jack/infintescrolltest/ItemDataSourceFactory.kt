package com.example.jack.infintescrolltest

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

class ItemDataSourceFactory : DataSource.Factory<Int, Item>() {

    val sourceLiveData = MutableLiveData<ItemDataSource>()

    override fun create(): DataSource<Int, Item> {
        val source = ItemDataSource()
        sourceLiveData.postValue(source)
        return source
    }
}