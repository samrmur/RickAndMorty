package com.saam.rickandmorty.characters.source

import com.saam.rickandmorty.api.models.Character
import com.saam.rickandmorty.api.services.CharactersService
import com.saam.rickandmorty.infrastructure.source.AbstractDataSource
import com.saam.rickandmorty.infrastructure.adapter.NetworkState
import timber.log.Timber
import kotlinx.coroutines.launch

class CharactersDataSource constructor(
    private val charactersService: CharactersService
): AbstractDataSource<Character>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Character>) {
        Timber.d("Loading page: %d", 1)

        networkState.postValue(NetworkState.LOADING)
        initialState.postValue(NetworkState.LOADING)

        this.launch(coroutineContext) {
            try {
                val characters = charactersService.getCharactersByPageAsync(1).await()

                setRetry(null)
                networkState.postValue(NetworkState.LOADED)
                initialState.postValue(NetworkState.LOADED)

                Timber.d("Page Loaded: %d", 1)

                if (!characters.info.next.isEmpty())
                    callback.onResult(characters.results, null, 2)
            } catch(err: Exception) {
                setRetry { loadInitial(params, callback) }
                val error = NetworkState.error(err.message)
                networkState.postValue(error)
                initialState.postValue(error)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        Timber.d("Loading page: %d", params.key)

        networkState.postValue(NetworkState.LOADING)

        this.launch(coroutineContext) {
            try {
                val characters = charactersService.getCharactersByPageAsync(params.key).await()

                setRetry(null)
                networkState.postValue(NetworkState.LOADED)

                Timber.d("Page Loaded: %d", params.key)

                if (!characters.info.next.isEmpty())
                    callback.onResult(characters.results, params.key + 1)
            } catch(err: Exception) {
                setRetry { loadAfter(params, callback) }
                networkState.postValue(NetworkState.error(err.message))
            }
        }
    }
}
