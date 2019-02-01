package com.saam.rickandmorty.characters.di

import androidx.lifecycle.Lifecycle
import com.saam.rickandmorty.characters.ui.CharactersFragment
import com.saam.rickandmorty.core.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class CharactersFragmentModule {
    @FragmentScope
    @Provides
    fun providesLifecycle(fragment: CharactersFragment): Lifecycle = fragment.lifecycle
}
