package com.saam.rickandmorty.api.models

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class EpisodeList(
    val info: Info,
    val results: List<Episode>
)