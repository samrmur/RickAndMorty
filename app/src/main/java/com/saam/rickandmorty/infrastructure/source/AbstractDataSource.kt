package com.saam.rickandmorty.infrastructure.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.saam.rickandmorty.infrastructure.adapter.NetworkState
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class AbstractDataSource<T>: PageKeyedDataSource<Int, T>(), CoroutineScope {
    protected val initialState = MutableLiveData<NetworkState>()
    protected val networkState = MutableLiveData<NetworkState>()
    private var retryCallback: (() -> Unit)? = null
    private val job: Job = Job()

    // This function is not needed, data is loaded and stored linearly, nothing should be lost
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {}

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    /**
     * Returns the initial network state
     * @return MutableLiveData<NetworkState>
     */
    fun getInitialLoadingState(): MutableLiveData<NetworkState> = initialState

    /**
     * Returns the network state
     * @return MutableLiveData<NetworkState>
     */
    fun getLoadingState(): MutableLiveData<NetworkState> = networkState

    /**
     * Attempts to retry fetching data from the server
     */
    fun retry() {
        retryCallback?.let { retry -> this.launch(coroutineContext) { retry() } }
    }

    fun cancel() {
        coroutineContext.cancel()
    }

    /**
     * Sets the retry action
     * @action Action
     */
    protected fun setRetry(action: (() -> Unit)?) {
        if (action == null)
            this.retryCallback = null
        else
            this.retryCallback = action
    }
}