package com.saam.rickandmorty.locationslist.source

import com.saam.rickandmorty.api.models.Location
import com.saam.rickandmorty.api.models.LocationPage
import com.saam.rickandmorty.api.services.LocationsService
import com.saam.rickandmorty.infrastructure.adapter.NetworkState
import com.saam.rickandmorty.infrastructure.source.AbstractDataSource
import timber.log.Timber
import kotlinx.coroutines.launch

class LocationsDataSource constructor(
    private val locationsService: LocationsService
): AbstractDataSource<Location>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Location>) {
        Timber.d("Loading Locations Page: %d", 1)

        networkState.postValue(NetworkState.LOADING)
        initialState.postValue(NetworkState.LOADING)

        this.launch(coroutineContext) {
            try {
                val locations = locationsService.getLocationsByPageAsync(1).await()

                Timber.d("Locations Page Loaded: %d", 1)

                setRetry(null)
                networkState.postValue(NetworkState.LOADED)
                initialState.postValue(NetworkState.LOADED)

                callback.onResult(locations.results, null, nextPage(locations, 2))
            } catch(err: Exception) {
                setRetry { loadInitial(params, callback) }
                val error = NetworkState.error(err.message)
                networkState.postValue(error)
                initialState.postValue(error)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Location>) {
        Timber.d("Loading Locations Page: %d", params.key)

        networkState.postValue(NetworkState.LOADING)

        this.launch(coroutineContext) {
            try {
                val locations = locationsService.getLocationsByPageAsync(params.key).await()

                Timber.d("Locations Page Loaded: %d", params.key)

                setRetry(null)
                networkState.postValue(NetworkState.LOADED)
                initialState.postValue(NetworkState.LOADED)

                callback.onResult(locations.results, nextPage(locations, params.key))
            } catch(err: Exception) {
                setRetry { loadAfter(params, callback) }
                val error = NetworkState.error(err.message)
                networkState.postValue(error)
            }
        }
    }

    private fun nextPage(page: LocationPage, pageNum: Int) = if (!page.info.next.isEmpty()) pageNum + 1 else null
}