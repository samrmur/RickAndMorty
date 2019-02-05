package com.saam.rickandmorty.episodes.presentation

import com.saam.rickandmorty.api.models.Episode
import com.saam.rickandmorty.episodes.source.EpisodesDataSource
import com.saam.rickandmorty.episodes.source.EpisodesDataSourceFactory
import com.saam.rickandmorty.infrastructure.presentation.PagedViewModel
import javax.inject.Inject

class EpisodesViewModel @Inject constructor(
    sourceFactory: EpisodesDataSourceFactory
): PagedViewModel<Episode, EpisodesDataSource>(sourceFactory)