package com.saam.rickandmorty.episodes.source

import androidx.paging.PageKeyedDataSource
import io.reactivex.disposables.CompositeDisposable
import androidx.lifecycle.MutableLiveData
import com.saam.rickandmorty.api.models.Episode
import com.saam.rickandmorty.api.services.EpisodesService
import com.saam.rickandmorty.infrastructure.filters.EpisodeFilter
import com.saam.rickandmorty.infrastructure.adapter.NetworkState
import timber.log.Timber
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class EpisodesDataSource constructor(
        private val episodesService: EpisodesService,
        private var episodeFilter: EpisodeFilter
): PageKeyedDataSource<Int, Episode>() {
    private val initialLoad = MutableLiveData<NetworkState>()
    private val networkState = MutableLiveData<NetworkState>()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var retryCompletable: Completable? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Episode>) {
        Timber.d("Loading page: %d", 1)

        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)
        compositeDisposable.add(episodesService.getEpisodesByPage(
                1,
                episodeFilter.name,
                        episodeFilter.air_date,
                episodeFilter.episode

        ).subscribe({ episodes ->
            setRetry(null)
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)

            if (episodes.info.next.isEmpty())
                callback.onResult(episodes.results, null, null)
            else
                callback.onResult(episodes.results, null, 2)
        }, { throwable ->
            setRetry(Action { loadInitial(params, callback) })
            val error = NetworkState.error(throwable.message)
            networkState.postValue(error)
            initialLoad.postValue(error)
        }))
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Episode>) {
        Timber.d("Loading page: %d", params.key)

        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(episodesService.getEpisodesByPage(
                params.key,
                episodeFilter.name,
                episodeFilter.air_date,
                episodeFilter.episode

        ).subscribe({ episodes ->
            setRetry(null)
            networkState.postValue(NetworkState.LOADED)

            if (episodes.info.next.isEmpty())
                callback.onResult(episodes.results, null)
            else
                callback.onResult(episodes.results, params.key + 1)
        }, { throwable ->
            setRetry(Action { loadAfter(params, callback) })
            networkState.postValue(NetworkState.error(throwable.message))
        }))
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Episode>) {
        // Do nothing
    }

    fun getDisposable(): CompositeDisposable = compositeDisposable

    fun getNetworkState(): MutableLiveData<NetworkState> = networkState

    fun retry() {
        if (retryCompletable != null)
            compositeDisposable.add(retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({}, {
                        throwable -> Timber.e("ERROR: %s", throwable.message)
                    }))
    }

    private fun setRetry(action: Action?) {
        if (action == null) {
            this.retryCompletable = null
        } else {
            this.retryCompletable = Completable.fromAction(action)
        }
    }
}