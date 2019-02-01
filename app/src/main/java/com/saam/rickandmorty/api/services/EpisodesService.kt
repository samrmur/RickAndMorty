package com.saam.rickandmorty.api.services

import com.saam.rickandmorty.api.models.Episode
import com.saam.rickandmorty.api.models.EpisodeList
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodesService {
    @GET("episode/")
    fun getEpisodesByPage(
            @Query("page") pageNum: Int?,
            @Query("name") name: String?,
            @Query("air_date") air_date: String?,
            @Query("episode") episode: String?
    ): Observable<EpisodeList>

    @GET("episode/{id}")
    fun getEpisode(
            @Path("id") episodeId: Int
    ): Observable<List<Episode>>
}