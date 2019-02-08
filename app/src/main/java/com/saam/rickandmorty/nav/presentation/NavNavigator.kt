package com.saam.rickandmorty.nav.presentation

import com.saam.rickandmorty.R
import com.saam.rickandmorty.characterslist.ui.CharactersFragment
import com.saam.rickandmorty.episodeslist.ui.EpisodesFragment
import com.saam.rickandmorty.locationslist.ui.LocationsFragment
import com.saam.rickandmorty.nav.ui.NavActivity
import com.saam.rickandmorty.util.extensions.replaceFragment

class NavNavigator(private val activity: NavActivity) {
    fun toCharacters() {
        activity.replaceFragment(CharactersFragment.TAG, R.id.fragment_container) {
            CharactersFragment.newInstance()
        }
    }

    fun toLocations() {
        activity.replaceFragment(LocationsFragment.TAG, R.id.fragment_container){
            LocationsFragment.newInstance()
        }
    }

    fun toEpisodes() {
        activity.replaceFragment(EpisodesFragment.TAG, R.id.fragment_container){
            EpisodesFragment.newInstance()
        }
    }
}