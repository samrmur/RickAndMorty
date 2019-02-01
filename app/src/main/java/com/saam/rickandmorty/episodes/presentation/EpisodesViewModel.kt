package com.saam.rickandmorty.episodes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.saam.rickandmorty.api.models.Episode
import com.saam.rickandmorty.episodes.source.EpisodesDataSource
import com.saam.rickandmorty.episodes.source.EpisodesDataSourceFactory
import com.saam.rickandmorty.infrastructure.filters.EpisodeFilter
import com.saam.rickandmorty.infrastructure.adapter.NetworkState
import javax.inject.Inject

class EpisodesViewModel @Inject constructor(
    private val sourceFactory: EpisodesDataSourceFactory
): ViewModel() {
    private var episodeList: LiveData<PagedList<Episode>>

    init {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(20)
                .build()
        episodeList = LivePagedListBuilder<Int, Episode>(sourceFactory, config).build()
    }

    fun getEpisodeList(): LiveData<PagedList<Episode>> = episodeList

    fun getNetworkState(): LiveData<NetworkState> =
            Transformations.switchMap<EpisodesDataSource, NetworkState>(
                    sourceFactory.getDataSourceLiveData()
            ) {
                it.getNetworkState()
            }

    fun retry() {
        sourceFactory.getDataSourceLiveData().value?.retry()
    }
}