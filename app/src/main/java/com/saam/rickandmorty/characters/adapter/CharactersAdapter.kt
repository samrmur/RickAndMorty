package com.saam.rickandmorty.characters.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.saam.rickandmorty.R
import com.saam.rickandmorty.api.models.Character
import com.saam.rickandmorty.infrastructure.adapter.GenericViewHolder
import com.saam.rickandmorty.infrastructure.adapter.NetworkStateAdapter

class CharactersAdapter(
    retryCallback: () -> Unit
): NetworkStateAdapter<Character>(
    diffCallback,
    R.layout.item_character,
    retryCallback
) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean = oldItem == newItem
        }
    }

    override fun getHolder(parent: ViewGroup): GenericViewHolder<Character> = CharacterViewHolder.create(parent)
}