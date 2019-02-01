package com.saam.rickandmorty.episodes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saam.rickandmorty.R
import com.saam.rickandmorty.api.models.Episode
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_episode.view.*

class EpisodeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindTo(episode: Episode?) {
        Picasso.get()
                .load(episode?.url)
                .placeholder(R.drawable.img_placeholder)
                .into(itemView.image)

        itemView.name.text = episode?.name
        itemView.air_date.text = episode?.airDate
    }

    companion object {
        fun create(parent: ViewGroup): EpisodeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_episode, parent, false)
            return EpisodeViewHolder(view)
        }
    }
}