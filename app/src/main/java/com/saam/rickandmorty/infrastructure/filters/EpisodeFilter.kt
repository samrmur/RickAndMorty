package com.saam.rickandmorty.infrastructure.filters

data class EpisodeFilter(
        val name: String? = null,
        val air_date: String? = null,
        val episode: String? = null
)