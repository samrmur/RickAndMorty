package com.saam.rickandmorty.api.models

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class CharacterPage(
    val info: Info,
    val results: List<Character>
)