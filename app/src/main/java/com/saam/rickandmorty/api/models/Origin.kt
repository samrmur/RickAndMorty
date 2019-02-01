package com.saam.rickandmorty.api.models

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class Origin(
    val name: String,
    val url: String
)