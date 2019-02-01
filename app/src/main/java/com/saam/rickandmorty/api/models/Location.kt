package com.saam.rickandmorty.api.models

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
)