package com.saam.rickandmorty.episodeslist.source

import androidx.paging.DataSource
import com.saam.rickandmorty.api.models.Episode
import com.saam.rickandmorty.api.services.EpisodesService
import com.saam.rickandmorty.infrastructure.source.AbstractDataSourceFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodesDataSourceFactory @Inject constructor(
        private val service: EpisodesService
): AbstractDataSourceFactory<Episode, EpisodesDataSource>() {
    override fun create(): DataSource<Int, Episode> {
        val episodesDataSource = EpisodesDataSource(service)
        liveDataSource.postValue(episodesDataSource)
        return episodesDataSource
    }
}