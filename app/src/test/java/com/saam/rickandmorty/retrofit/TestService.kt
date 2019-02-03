package com.saam.rickandmorty.retrofit

import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface TestService {
    @GET("character/")
    fun getCharactersWithRx(@Query("page") pageNum: Int): Observable<ResponseBody>

    @GET("character/")
    fun getCharactersAsync(@Query("page") pageNum: Int): Deferred<ResponseBody>
}