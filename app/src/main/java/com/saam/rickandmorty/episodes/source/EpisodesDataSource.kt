package com.saam.rickandmorty.episodes.source

import com.saam.rickandmorty.api.models.Episode
import com.saam.rickandmorty.api.models.EpisodePage
import com.saam.rickandmorty.api.services.EpisodesService
import com.saam.rickandmorty.infrastructure.adapter.NetworkState
import com.saam.rickandmorty.infrastructure.source.AbstractDataSource
import timber.log.Timber
import kotlinx.coroutines.launch

class EpisodesDataSource constructor(
    private val episodesService: EpisodesService
): AbstractDataSource<Episode>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Episode>) {
        Timber.d("Loading Episodes Page: %d", 1)

        networkState.postValue(NetworkState.LOADING)
        initialState.postValue(NetworkState.LOADING)

        this.launch(coroutineContext) {
            try {
                val episodes = episodesService.getEpisodesByPageAsync(1).await()

                setRetry(null)
                networkState.postValue(NetworkState.LOADED)
                initialState.postValue(NetworkState.LOADED)

                Timber.d("Episodes Page Loaded: %d", 1)

                callback.onResult(episodes.results, null, nextPage(episodes, 1))
            } catch (err: Exception) {
                setRetry { loadInitial(params, callback) }
                val error = NetworkState.error(err.message)
                networkState.postValue(error)
                initialState.postValue(error)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Episode>) {
        Timber.d("Loading Episodes Page: %d", params.key)

        networkState.postValue(NetworkState.LOADING)

        this.launch(coroutineContext) {
            try {
                val episodes = episodesService.getEpisodesByPageAsync(params.key).await()

                setRetry(null)
                networkState.postValue(NetworkState.LOADED)
                initialState.postValue(NetworkState.LOADED)

                Timber.d("Episodes Page Loaded: %d", params.key)

                callback.onResult(episodes.results, nextPage(episodes, params.key))
            } catch (err: Exception) {
                setRetry { loadAfter(params, callback) }
                val error = NetworkState.error(err.message)
                networkState.postValue(error)
            }
        }
    }

    private fun nextPage(page: EpisodePage, pageNum: Int) = if (!page.info.next.isEmpty()) pageNum + 1 else null
}