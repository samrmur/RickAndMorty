package com.saam.rickandmorty.infrastructure.adapter

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
    val status: NetworkStatus,
    val message: String? = null
) {
    companion object {
        val LOADED = NetworkState(NetworkStatus.SUCCESS)
        val LOADING = NetworkState(NetworkStatus.RUNNING)
        fun error(msg: String?) = NetworkState(NetworkStatus.FAILED, msg)
    }
}