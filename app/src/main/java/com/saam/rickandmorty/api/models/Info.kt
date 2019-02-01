package com.saam.rickandmorty.api.models

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class Info(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String
)