package com.saam.rickandmorty.infrastructure.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.saam.rickandmorty.infrastructure.adapter.NetworkState
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

abstract class AbstractDataSource<T>: PageKeyedDataSource<Int, T>() {
    protected val initialState = MutableLiveData<NetworkState>()
    protected val networkState = MutableLiveData<NetworkState>()
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()
    protected var retry: Completable? = null

    // This function is not needed, data is loaded and stored linearly, nothing should be lost
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {}

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
        retry?.let { completable ->
            compositeDisposable.add(completable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, {
                    throwable -> Timber.e("ERROR: %s", throwable.message)
                }))
        }
    }

    /**
     * Sets the retry action
     * @action Action
     */
    protected fun setRetry(action: Action?) {
        if (action == null)
            this.retry = null
        else
            this.retry = Completable.fromAction(action)
    }
}