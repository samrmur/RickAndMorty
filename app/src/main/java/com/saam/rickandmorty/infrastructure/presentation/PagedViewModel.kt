package com.saam.rickandmorty.infrastructure.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.saam.rickandmorty.infrastructure.adapter.NetworkState
import com.saam.rickandmorty.infrastructure.source.AbstractDataSource
import com.saam.rickandmorty.infrastructure.source.AbstractDataSourceFactory

open class PagedViewModel<T, Y: AbstractDataSource<T>> constructor(
    private val sourceFactory: AbstractDataSourceFactory<T, Y>
): ViewModel() {
    private var livePagedList: LiveData<PagedList<T>>

    init {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(20)
                .build()
        livePagedList = LivePagedListBuilder<Int, T>(sourceFactory, config).build()
    }

    override fun onCleared() {
        super.onCleared()
        sourceFactory.getLiveSource().value?.cancel()
    }

    fun getCharacterList(): LiveData<PagedList<T>> = livePagedList

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<Y, NetworkState>(
            sourceFactory.getLiveSource()
        ) { source ->
            source.getLoadingState()
        }
    }

    fun getInitialNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<Y, NetworkState>(
            sourceFactory.getLiveSource()
        ) { source ->
            source.getInitialLoadingState()
        }
    }

    fun retry() {
        sourceFactory.getLiveSource().value?.retry()
    }
}