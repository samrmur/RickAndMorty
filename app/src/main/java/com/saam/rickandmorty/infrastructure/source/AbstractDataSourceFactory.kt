package com.saam.rickandmorty.infrastructure.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

abstract class AbstractDataSourceFactory<T, Y: AbstractDataSource<T>>: DataSource.Factory<Int, T>() {
    protected val liveDataSource: MutableLiveData<Y> = MutableLiveData()

    fun getLiveSource(): MutableLiveData<Y> = liveDataSource
}