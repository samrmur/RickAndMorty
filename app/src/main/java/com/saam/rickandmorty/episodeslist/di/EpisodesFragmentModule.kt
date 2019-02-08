package com.saam.rickandmorty.episodeslist.di

import androidx.lifecycle.Lifecycle
import com.saam.rickandmorty.episodeslist.ui.EpisodesFragment
import com.saam.rickandmorty.core.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class EpisodesFragmentModule {
    @FragmentScope
    @Provides
    fun providesLifecycle(fragment: EpisodesFragment): Lifecycle = fragment.lifecycle
}