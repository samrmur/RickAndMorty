package com.saam.rickandmorty.api.services

import com.saam.rickandmorty.api.models.Character
import com.saam.rickandmorty.api.models.CharacterPage
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersService {
    @GET("character/")
    fun getCharactersByPage(
        @Query("page") pageNum: Int,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("type") type: String? = null,
        @Query("gender") gender: String? = null
    ): Deferred<CharacterPage>

    @GET("character/")
    fun getCharactersByPageAsync(@Query("page") pageNum: Int): Deferred<CharacterPage>

    @GET("character/{id}")
    fun getCharacter(
        @Path("id") characterId: Int
    ): Deferred<List<Character>>
}