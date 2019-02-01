package com.saam.rickandmorty.characters.source

import androidx.paging.DataSource
import com.saam.rickandmorty.api.models.Character
import com.saam.rickandmorty.api.services.CharactersService
import com.saam.rickandmorty.infrastructure.source.AbstractDataSourceFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersDataSourceFactory @Inject constructor(
    private val service: CharactersService
): AbstractDataSourceFactory<Character, CharactersDataSource>() {
    override fun create(): DataSource<Int, Character> {
        val charactersDataSource = CharactersDataSource(service)
        liveDataSource.postValue(charactersDataSource)
        return charactersDataSource
    }
}