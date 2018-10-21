package com.example.jack.infintescrolltest

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.lifecycle.Transformations

class ItemViewModel : ViewModel() {

    private val myPagingConfig = PagedList.Config.Builder()
        .setPageSize(20)
        .build()
    private val itemDataSourceFactory = ItemDataSourceFactory()

    val items = LivePagedListBuilder(itemDataSourceFactory, myPagingConfig)
        .build()

    val loadBeforeResource: LiveData<Resource<List<Item>>> = Transformations.switchMap(itemDataSourceFactory.sourceLiveData) { it.loadBeforeResource }
    val loadAfterResource: LiveData<Resource<List<Item>>> = Transformations.switchMap(itemDataSourceFactory.sourceLiveData) { it.loadAfterResource }

    fun fetchItems(position: Int) {}
}
