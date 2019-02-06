package com.saam.rickandmorty.api.services

import com.saam.rickandmorty.api.models.Episode
import com.saam.rickandmorty.api.models.EpisodePage
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodesService {
    @GET("episode/")
    fun getEpisodesByPageAsync(
            @Query("page") pageNum: Int,
            @Query("name") name: String? = null,
            @Query("air_date") air_date: String? = null,
            @Query("episode") episode: String? = null
    ): Deferred<EpisodePage>

    @GET("episode/{id}")
    fun getEpisode(
            @Path("id") episodeId: Int
    ): Deferred<List<Episode>>
}