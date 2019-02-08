package com.saam.rickandmorty.episodeslist.di

import com.saam.rickandmorty.episodeslist.ui.EpisodesFragment
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