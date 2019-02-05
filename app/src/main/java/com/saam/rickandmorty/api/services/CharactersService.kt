package com.saam.rickandmorty.api.services

import com.saam.rickandmorty.api.models.Character
import com.saam.rickandmorty.api.models.CharacterPage
import io.reactivex.Observable
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
    ): Observable<CharacterPage>

    @GET("character/")
    fun getCharactersByPageAsync(@Query("page") pageNum: Int): Deferred<CharacterPage>

    @GET("character/{id}")
    fun getCharacter(
        @Path("id") characterId: Int
    ): Observable<List<Character>>
}