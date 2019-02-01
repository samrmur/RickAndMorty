package com.saam.rickandmorty.locations.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.saam.rickandmorty.api.models.Location
import com.saam.rickandmorty.locations.source.LocationsDataSource
import com.saam.rickandmorty.locations.source.LocationsDataSourceFactory
import com.saam.rickandmorty.infrastructure.filters.LocationFilter
import com.saam.rickandmorty.infrastructure.adapter.NetworkState
import javax.inject.Inject

class LocationsViewModel @Inject constructor(
        private val sourceFactory: LocationsDataSourceFactory
): ViewModel() {
    private var locationList: LiveData<PagedList<Location>>

    init {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(20)
                .build()
        locationList = LivePagedListBuilder<Int, Location>(sourceFactory, config).build()
    }

    fun getLocationList(): LiveData<PagedList<Location>> = locationList

    fun getNetworkState(): LiveData<NetworkState> =
            Transformations.switchMap<LocationsDataSource, NetworkState>(
                    sourceFactory.getDataSourceLiveData()
            ) {
                it.getNetworkState()
            }

    fun filterLocations(filter: LocationFilter) {
        sourceFactory.setFilter(filter)
    }

    fun retry() {
        sourceFactory.getDataSourceLiveData().value?.retry()
    }
}