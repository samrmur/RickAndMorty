package com.saam.rickandmorty.episodeslist.presentation

import com.saam.rickandmorty.api.models.Episode
import com.saam.rickandmorty.episodeslist.source.EpisodesDataSource
import com.saam.rickandmorty.episodeslist.source.EpisodesDataSourceFactory
import com.saam.rickandmorty.infrastructure.presentation.PagedNetworkViewModel
import javax.inject.Inject

class EpisodesViewModel @Inject constructor(
    sourceFactory: EpisodesDataSourceFactory
): PagedNetworkViewModel<Episode, EpisodesDataSource>(sourceFactory)