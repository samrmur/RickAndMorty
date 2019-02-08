package com.saam.rickandmorty.locationslist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.saam.rickandmorty.R
import com.saam.rickandmorty.api.models.Location
import com.saam.rickandmorty.infrastructure.adapter.GenericViewHolder
import com.saam.rickandmorty.infrastructure.adapter.NetworkStateAdapter

class LocationsAdapter(
    private val retryCallback: () -> Unit
): NetworkStateAdapter<Location>(
    diffCallback,
    R.layout.item_location,
    retryCallback
) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Location>() {
            override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getHolder(parent: ViewGroup): GenericViewHolder<Location> = LocationViewHolder.create(parent)
}