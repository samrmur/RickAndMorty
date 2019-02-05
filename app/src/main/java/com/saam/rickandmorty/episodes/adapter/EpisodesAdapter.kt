package com.saam.rickandmorty.episodes.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.saam.rickandmorty.R
import com.saam.rickandmorty.api.models.Episode
import com.saam.rickandmorty.infrastructure.adapter.GenericViewHolder
import com.saam.rickandmorty.infrastructure.adapter.NetworkStateAdapter

class EpisodesAdapter(
    retryCallback: () -> Unit
): NetworkStateAdapter<Episode>(
    diffCallback,
    R.layout.item_character,
    retryCallback
) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Episode>() {
            override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getHolder(parent: ViewGroup): GenericViewHolder<Episode> = EpisodeViewHolder.create(parent)
}