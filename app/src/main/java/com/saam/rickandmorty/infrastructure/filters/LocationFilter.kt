package com.saam.rickandmorty.infrastructure.filters

data class LocationFilter(
        val name: String? = null,
        val dimension: String? = null,
        val type: String? = null
)