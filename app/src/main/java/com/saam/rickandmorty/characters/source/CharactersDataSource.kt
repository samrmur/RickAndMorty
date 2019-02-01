package com.saam.rickandmorty.characters.source

import com.saam.rickandmorty.api.models.Character
import com.saam.rickandmorty.api.services.CharactersService
import com.saam.rickandmorty.infrastructure.source.AbstractDataSource
import com.saam.rickandmorty.infrastructure.adapter.NetworkState
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import io.reactivex.functions.Action

class CharactersDataSource constructor(
    private val charactersService: CharactersService
): AbstractDataSource<Character>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Character>) {
        Timber.d("Loading page: %d", 1)

        networkState.postValue(NetworkState.LOADING)
        initialState.postValue(NetworkState.LOADING)
        compositeDisposable.add(charactersService.getCharactersByPage(1).subscribe({ characters ->
            setRetry(null)
            networkState.postValue(NetworkState.LOADED)
            initialState.postValue(NetworkState.LOADED)

            if (!characters.info.next.isEmpty())
                callback.onResult(characters.results, null, 2)
        }, { throwable ->
            setRetry(Action { loadInitial(params, callback) })
            val error = NetworkState.error(throwable.message)
            networkState.postValue(error)
            initialState.postValue(error)
        }))
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        Timber.d("Loading page: %d", params.key)

        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(charactersService.getCharactersByPage(params.key).subscribe({ characters ->
            setRetry(null)
            networkState.postValue(NetworkState.LOADED)

            if (!characters.info.next.isEmpty())
                callback.onResult(characters.results, params.key + 1)
        }, { throwable ->
            setRetry(Action { loadAfter(params, callback) })
            networkState.postValue(NetworkState.error(throwable.message))
        }))
    }
}
