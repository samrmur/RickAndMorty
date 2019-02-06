package com.saam.rickandmorty.characters.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saam.rickandmorty.R
import com.saam.rickandmorty.api.models.Character
import com.saam.rickandmorty.infrastructure.adapter.GenericViewHolder
import kotlinx.android.synthetic.main.item_character.view.*

class CharacterViewHolder(view: View): GenericViewHolder<Character>(view) {
    companion object {
        fun create(parent: ViewGroup): CharacterViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(  R.layout.item_character, parent, false)
            return CharacterViewHolder(view)
        }
    }

    override fun bindTo(model: Character?) {
        val resources = itemView.context.resources

        itemView.name.text = model?.name
        itemView.status.text = resources.getString(R.string.msg_character_status, model?.status)
        itemView.species.text = resources.getString(R.string.msg_character_species, model?.species)
        itemView.origin.text = resources.getString(R.string.msg_character_origin, model?.origin?.name)
        itemView.image.setImageURI(model?.image)
    }
}