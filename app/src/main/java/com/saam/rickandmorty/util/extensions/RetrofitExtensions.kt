package com.saam.rickandmorty.util.extensions

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun<T> Call<T>.onEnqueue(actOnSuccess: (Response<T>) -> Unit, actOnFailure: (t: Throwable?) -> Unit)   {
    this.enqueue(object: Callback<T>    {
        override fun onFailure(call: Call<T>?, t: Throwable?) {
            actOnFailure(t)
        }

        override fun onResponse(call: Call<T>?, response: Response<T>) {
            actOnSuccess(response)
        }
    })
}