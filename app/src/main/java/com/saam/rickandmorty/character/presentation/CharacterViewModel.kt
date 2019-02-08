package com.saam.rickandmorty.character.presentation

import androidx.lifecycle.MutableLiveData
import com.saam.rickandmorty.api.models.Character
import com.saam.rickandmorty.api.services.CharactersService
import com.saam.rickandmorty.infrastructure.adapter.NetworkState
import com.saam.rickandmorty.infrastructure.presentation.NetworkViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class CharacterViewModel @Inject constructor(
    private val service: CharactersService
): NetworkViewModel() {
    private val data: MutableLiveData<List<Character>> = MutableLiveData()

    fun getEpisodes(episodeIds: List<String>) {
        liveNetworkState.postValue(NetworkState.LOADING)

        Timber.d("Loading Episodes!")

        val episodesStr = episodeIds.joinToString(separator = ",")

        this.launch(coroutineContext) {
            try {
                data.postValue(service.getCharactersByIdAsync(episodesStr).await())
                liveNetworkState.postValue(NetworkState.LOADED)
                Timber.d("Episodes Loaded")
            } catch(err: Exception) {
                liveNetworkState.postValue(NetworkState.error(err.message))

                Timber.d("Episodes failed to load!")
                Timber.d(err)
            }

        }
    }
}