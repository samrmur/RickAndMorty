package com.saam.rickandmorty.episodes.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.saam.rickandmorty.api.models.Episode
import com.saam.rickandmorty.api.services.EpisodesService
import com.saam.rickandmorty.episodes.source.EpisodesDataSource
import com.saam.rickandmorty.infrastructure.filters.EpisodeFilter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodesDataSourceFactory @Inject constructor(
        private val service: EpisodesService
) : DataSource.Factory<Int, Episode>() {
    private val episodesDataSourceLiveData: MutableLiveData<EpisodesDataSource> = MutableLiveData()
    private var filter: EpisodeFilter = EpisodeFilter()

    override fun create(): DataSource<Int, Episode> {
        val episodesDataSource = EpisodesDataSource(service, filter)
        episodesDataSourceLiveData.postValue(episodesDataSource)
        return episodesDataSource
    }

    fun getDataSourceLiveData(): MutableLiveData<EpisodesDataSource> {
        return episodesDataSourceLiveData
    }

    fun setFilter(filter: EpisodeFilter) {
        this.filter = filter
        episodesDataSourceLiveData.value?.invalidate()
        create()
    }
}