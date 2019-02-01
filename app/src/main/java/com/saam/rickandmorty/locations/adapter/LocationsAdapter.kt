package com.saam.rickandmorty.locations.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.saam.rickandmorty.R
import com.saam.rickandmorty.api.models.Location
import com.saam.rickandmorty.infrastructure.adapter.NetworkState
import com.saam.rickandmorty.infrastructure.adapter.NetworkStateViewHolder

class LocationsAdapter(
        private val retryCallback: () -> Unit
) : PagedListAdapter<Location, RecyclerView.ViewHolder>(UserDiffCallback) {
    private var networkState: NetworkState? = null

    companion object {
        val UserDiffCallback = object : DiffUtil.ItemCallback<Location>() {
            override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_location -> LocationViewHolder.create(parent)
            R.layout.item_network_state -> NetworkStateViewHolder.create(parent, retryCallback)
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_location -> (holder as LocationViewHolder).bindTo(getItem(position))
            R.layout.item_network_state -> (holder as NetworkStateViewHolder).bindTo(networkState)
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            R.layout.item_location
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        if (currentList != null) {
            if (currentList!!.size != 0) {
                val previousState = this.networkState
                val hadExtraRow = hasExtraRow()

                this.networkState = newNetworkState
                val hasExtraRow = hasExtraRow()

                if (hadExtraRow != hasExtraRow) {
                    if (hadExtraRow)
                        notifyItemRemoved(super.getItemCount())
                    else
                        notifyItemInserted(super.getItemCount())
                } else if (hasExtraRow && previousState !== newNetworkState)
                    notifyItemChanged(itemCount - 1)
            }
        }
    }
}