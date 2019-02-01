package com.saam.rickandmorty.api.models

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class LocationList(
    val info: Info,
    val results: List<Location>
)