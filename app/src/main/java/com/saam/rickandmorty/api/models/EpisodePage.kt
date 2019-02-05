package com.saam.rickandmorty.api.models

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class EpisodePage(
    val info: Info,
    val results: List<Episode>
)