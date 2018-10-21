package com.example.jack.infintescrolltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = getViewModel()

        val itemAdapter = ItemAdapter()
        list.adapter = itemAdapter
        viewModel.items.observe(this, Observer<PagedList<Item>> {
            itemAdapter.submitList(it)
        })

        viewModel.loadAfterResource.observe(this, Observer<Resource<List<Item>>> {
            itemAdapter.isLoadingAfter = Resource.Status.LOADING == it.status
        })

        viewModel.loadBeforeResource.observe(this, Observer<Resource<List<Item>>> {
            itemAdapter.isLoadingBefore = Resource.Status.LOADING == it.status
        })
    }

    private fun getViewModel(): ItemViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ItemViewModel() as T
            }
        })[ItemViewModel::class.java]
    }
}
