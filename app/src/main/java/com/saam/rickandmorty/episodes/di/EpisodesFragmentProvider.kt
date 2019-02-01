package com.saam.rickandmorty.episodes.di

import com.saam.rickandmorty.episodes.di.EpisodesFragmentModule
import com.saam.rickandmorty.episodes.ui.EpisodesFragment
import com.saam.rickandmorty.core.scope.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class EpisodesFragmentProvider {
    @FragmentScope
    @ContributesAndroidInjector(modules = [
        EpisodesFragmentModule::class
    ])
    abstract fun providesEpisodesFragment(): EpisodesFragment
}