package com.saam.rickandmorty.api.services

import com.saam.rickandmorty.api.models.Location
import com.saam.rickandmorty.api.models.LocationPage
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationsService {
    @GET("location/")
    fun getLocationsByPageAsync(
        @Query("page") pageNum: Int,
        @Query("name") name: String? = null,
        @Query("dimension") dimension: String? = null,
        @Query("type") type: String? = null
    ): Deferred<LocationPage>

    @GET("location/{id}")
    fun getLocation(
        @Path("id") locationId: Int
    ): Deferred<List<Location>>
}