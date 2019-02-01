package com.saam.rickandmorty.characters.presentation

import com.saam.rickandmorty.api.models.Character
import com.saam.rickandmorty.characters.source.CharactersDataSource
import com.saam.rickandmorty.characters.source.CharactersDataSourceFactory
import com.saam.rickandmorty.infrastructure.presentation.PagedViewModel
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    sourceFactory: CharactersDataSourceFactory
): PagedViewModel<Character, CharactersDataSource>(sourceFactory)