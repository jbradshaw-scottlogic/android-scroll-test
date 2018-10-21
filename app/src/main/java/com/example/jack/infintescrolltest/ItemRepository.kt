package com.example.jack.infintescrolltest

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import kotlin.math.max
import kotlin.math.min

class ItemRepository {

    private val executor: ExecutorService = Executors.newCachedThreadPool()
    private val random: Random = Random()
    private val items = (1..2000).map { Item(it, it.toString()) }

    fun getItems(position: Int, pageSize: Int): Future<Resource<List<Item>>> {
        val startIndex = max(position, 0)
        val endIndex = min(items.size, position + pageSize)
        val liveData = MutableLiveData<Resource<List<Item>>>()
        liveData.postValue(Resource.loading())

        return getFuture(Resource.success(items.subList(startIndex, endIndex)))
    }

    private fun <T> getFuture(value: T): Future<T> {
        return executor.submit(Callable<T> {
            Thread.sleep(nextInterval())
            value
        })
    }

    private fun nextInterval(): Long {
        return (1000  + (random.nextFloat() * 1000)).toLong()
    }
}