package com.saam.rickandmorty.locationslist.presentation

import com.saam.rickandmorty.api.models.Location
import com.saam.rickandmorty.locationslist.source.LocationsDataSource
import com.saam.rickandmorty.locationslist.source.LocationsDataSourceFactory
import com.saam.rickandmorty.infrastructure.presentation.PagedNetworkViewModel
import javax.inject.Inject

class LocationsViewModel @Inject constructor(
    sourceFactory: LocationsDataSourceFactory
): PagedNetworkViewModel<Location, LocationsDataSource>(sourceFactory)