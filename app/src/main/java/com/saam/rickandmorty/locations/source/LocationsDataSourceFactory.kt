package com.saam.rickandmorty.locations.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.saam.rickandmorty.api.models.Location
import com.saam.rickandmorty.api.services.LocationsService
import com.saam.rickandmorty.infrastructure.filters.LocationFilter
import com.saam.rickandmorty.locations.source.LocationsDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationsDataSourceFactory @Inject constructor(
        private val service: LocationsService
) : DataSource.Factory<Int, Location>() {
    private val locationsDataSourceLiveData: MutableLiveData<LocationsDataSource> = MutableLiveData()
    private var filter: LocationFilter = LocationFilter()

    override fun create(): DataSource<Int, Location> {
        val locationsDataSource = LocationsDataSource(service, filter)
        locationsDataSourceLiveData.postValue(locationsDataSource)
        return locationsDataSource
    }

    fun getDataSourceLiveData(): MutableLiveData<LocationsDataSource> {
        return locationsDataSourceLiveData
    }

    fun setFilter(filter: LocationFilter) {
        this.filter = filter
        locationsDataSourceLiveData.value?.invalidate()
        create()
    }
}