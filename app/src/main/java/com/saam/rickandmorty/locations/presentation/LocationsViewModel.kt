package com.saam.rickandmorty.locations.presentation

import com.saam.rickandmorty.api.models.Location
import com.saam.rickandmorty.locations.source.LocationsDataSource
import com.saam.rickandmorty.locations.source.LocationsDataSourceFactory
import com.saam.rickandmorty.infrastructure.presentation.PagedViewModel
import javax.inject.Inject

class LocationsViewModel @Inject constructor(
    sourceFactory: LocationsDataSourceFactory
): PagedViewModel<Location, LocationsDataSource>(sourceFactory)