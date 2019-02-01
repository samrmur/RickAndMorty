package com.saam.rickandmorty.infrastructure.adapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.saam.rickandmorty.R

@Suppress("UNCHECKED_CAST")
abstract class NetworkStateAdapter<T>(
    diffCallback: DiffUtil.ItemCallback<T>,
    @LayoutRes private val layoutId: Int,
    private val retryCallback: () -> Unit
): PagedListAdapter<T, RecyclerView.ViewHolder>(diffCallback) {
    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            layoutId -> getHolder(parent)
            R.layout.item_network_state -> NetworkStateViewHolder.create(parent, retryCallback)
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            layoutId -> (holder as GenericViewHolder<T>).bindTo(getItem(position))
            R.layout.item_network_state -> (holder as NetworkStateViewHolder).bindTo(networkState)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasLoadingView() && position == itemCount - 1) R.layout.item_network_state else layoutId
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasLoadingView()) 1 else 0
    }

    /**
     * Returns instance of a class that extends GenericViewHolder
     * @return GenericViewHolder<T>
     */
    abstract fun getHolder(parent: ViewGroup): GenericViewHolder<T>

    /**
     * Sets the current state of the network
     * @return NetworkState?
     */
    fun setNetworkState(newNetworkState: NetworkState?) {
        currentList?.let { list ->
            if (list.size != 0) {
                val previousState = this.networkState
                val didLoadingViewExist = hasLoadingView()

                this.networkState = newNetworkState
                val doesLoadingViewExist = hasLoadingView()

                if (didLoadingViewExist != doesLoadingViewExist) {
                    if (didLoadingViewExist)
                        notifyItemRemoved(super.getItemCount())
                    else
                        notifyItemInserted(super.getItemCount())
                } else if (doesLoadingViewExist && previousState !== newNetworkState)
                    notifyItemChanged(itemCount - 1)
            }
        }
    }

    /**
     * Checks if a loading item view is currently inserted in the list
     * @return Boolean
     */
    private fun hasLoadingView(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }
}