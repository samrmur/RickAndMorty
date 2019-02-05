package com.saam.rickandmorty.api.models

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class Episode(
    val id: Int,
    val name: String,
    @Json(name = "air_date") val airDate: String,
    val season: Int,
    val number: Int,
    val characters: List<String>,
    val url: String,
    val created: String
)