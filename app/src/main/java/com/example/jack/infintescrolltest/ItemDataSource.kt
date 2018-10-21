package com.example.jack.infintescrolltest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ItemDataSource: ItemKeyedDataSource<Int, Item>() {

    private val executor: ExecutorService = Executors.newCachedThreadPool()
    private val itemRepo = ItemRepository()
    private var mLoadBeforeResource: MutableLiveData<Resource<List<Item>>> = MutableLiveData()
    private var mLoadAfterResource: MutableLiveData<Resource<List<Item>>> = MutableLiveData()

    var loadBeforeResource: LiveData<Resource<List<Item>>> = mLoadBeforeResource
    var loadAfterResource: LiveData<Resource<List<Item>>> = mLoadAfterResource


    override fun getKey(item: Item): Int {
        return item.id
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Item>) {
        val itemsFuture = itemRepo.getItems(params.requestedInitialKey ?: 1000, params.requestedLoadSize)
        executor.execute {
            val resource = itemsFuture.get()
                when(resource.status) {
                Resource.Status.SUCCESS -> callback.onResult(resource.data!!)
            }
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Item>) {
        val itemsFuture = itemRepo.getItems(params.key, params.requestedLoadSize)
        mLoadAfterResource.postValue(Resource.loading())
        executor.execute {
            val resource = itemsFuture.get()
            mLoadAfterResource.postValue(resource)
            when (resource.status) {
                Resource.Status.SUCCESS -> callback.onResult(resource.data!!)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Item>) {
        val itemsFuture = itemRepo.getItems((params.key - 1) - params.requestedLoadSize, params.requestedLoadSize)
        mLoadBeforeResource.postValue(Resource.loading())
        executor.execute {
            val resource = itemsFuture.get()
            mLoadBeforeResource.postValue(resource)
            when (resource.status) {
                Resource.Status.SUCCESS -> callback.onResult(resource.data!!)
            }
        }
    }
}
