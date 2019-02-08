package com.saam.rickandmorty.episodeslist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saam.rickandmorty.R
import com.saam.rickandmorty.api.models.Episode
import com.saam.rickandmorty.infrastructure.adapter.GenericViewHolder
import kotlinx.android.synthetic.main.item_episode.view.*

class EpisodeViewHolder(view: View): GenericViewHolder<Episode>(view) {
    companion object {
        fun create(parent: ViewGroup): EpisodeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_episode, parent, false)
            return EpisodeViewHolder(view)
        }
    }

    override fun bindTo(model: Episode?) {
        val resources = itemView.resources

        if (model?.number == 1)
            itemView.header.visibility = View.VISIBLE
        else
            itemView.header.visibility = View.GONE

        itemView.header.text = resources.getString(R.string.msg_episode_season_number, model?.season)
        itemView.name.text = model?.name
        itemView.episode_number.text = resources.getString(R.string.msg_episode_number, model?.number)
        itemView.air_date.text = resources.getString(R.string.msg_episode_air_date, model?.airDate)
    }
}