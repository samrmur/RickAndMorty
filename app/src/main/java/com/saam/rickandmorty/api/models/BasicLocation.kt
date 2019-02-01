package com.saam.rickandmorty.api.models

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class BasicLocation(
    val name: String,
    val url: String
)