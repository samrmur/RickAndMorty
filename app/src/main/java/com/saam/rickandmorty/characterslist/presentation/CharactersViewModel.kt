package com.saam.rickandmorty.characterslist.presentation

import com.saam.rickandmorty.api.models.Character
import com.saam.rickandmorty.characterslist.source.CharactersDataSource
import com.saam.rickandmorty.characterslist.source.CharactersDataSourceFactory
import com.saam.rickandmorty.infrastructure.presentation.PagedNetworkViewModel
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    sourceFactory: CharactersDataSourceFactory
): PagedNetworkViewModel<Character, CharactersDataSource>(sourceFactory)