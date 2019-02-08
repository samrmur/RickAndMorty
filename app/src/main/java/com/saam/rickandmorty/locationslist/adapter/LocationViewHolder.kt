package com.saam.rickandmorty.locationslist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saam.rickandmorty.R
import com.saam.rickandmorty.api.models.Location
import com.saam.rickandmorty.infrastructure.adapter.GenericViewHolder
import kotlinx.android.synthetic.main.item_location.view.*

class LocationViewHolder(view: View): GenericViewHolder<Location>(view) {
    companion object {
        fun create(parent: ViewGroup): LocationViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_location, parent, false)
            return LocationViewHolder(view)
        }
    }

    override fun bindTo(model: Location?) {
        itemView.name.text = model?.name
        itemView.dimension.text = itemView.resources.getString(R.string.msg_location_dimension, model?.dimension)
    }
}