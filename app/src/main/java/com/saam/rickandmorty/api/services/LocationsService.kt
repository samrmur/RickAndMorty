package com.saam.rickandmorty.api.services

import com.saam.rickandmorty.api.models.Location
import com.saam.rickandmorty.api.models.LocationList
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationsService {
    @GET("location/")
    fun getLocationsByPage(
            @Query("page") pageNum: Int?,
            @Query("name") name: String?,
            @Query("dimension") dimension: String?,
            @Query("type") type: String?

    ): Observable<LocationList>

    @GET("location/{id}")
    fun getLocation(
            @Path("id") locationId: Int
    ): Observable<List<Location>>
}