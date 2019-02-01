package com.saam.rickandmorty.locations.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saam.rickandmorty.R
import com.saam.rickandmorty.api.models.Location
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_location.view.*

class LocationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindTo(location: Location?) {
        Picasso.get()
                .load(location?.url)
                .placeholder(R.drawable.img_placeholder)
                .into(itemView.image)

        itemView.name.text = location?.name
        itemView.dimension.text = location?.dimension
    }

    companion object {
        fun create(parent: ViewGroup): LocationViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_location, parent, false)
            return LocationViewHolder(view)
        }
    }

}