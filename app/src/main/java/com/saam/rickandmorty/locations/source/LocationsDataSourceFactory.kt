package com.saam.rickandmorty.locations.source

import androidx.paging.DataSource
import com.saam.rickandmorty.api.models.Location
import com.saam.rickandmorty.api.services.LocationsService
import com.saam.rickandmorty.infrastructure.source.AbstractDataSourceFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationsDataSourceFactory @Inject constructor(
    private val service: LocationsService
): AbstractDataSourceFactory<Location, LocationsDataSource>() {
    override fun create(): DataSource<Int, Location> {
        val locationsDataSource = LocationsDataSource(service)
        liveDataSource.postValue(locationsDataSource)
        return locationsDataSource
    }
}