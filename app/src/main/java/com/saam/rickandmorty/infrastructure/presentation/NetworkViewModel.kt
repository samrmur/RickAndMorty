package com.saam.rickandmorty.infrastructure.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saam.rickandmorty.infrastructure.adapter.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

open class NetworkViewModel: ViewModel(), CoroutineScope {
    private val job: Job = Job()
    protected val liveNetworkState: MutableLiveData<NetworkState> = MutableLiveData()

    override val coroutineContext: CoroutineContext get() = job + Dispatchers.IO

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }
}
