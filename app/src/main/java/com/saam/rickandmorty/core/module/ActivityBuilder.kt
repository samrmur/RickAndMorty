package com.saam.rickandmorty.core.module

import com.saam.rickandmorty.nav.di.NavModule
import com.saam.rickandmorty.characterslist.di.CharactersFragmentProvider
import com.saam.rickandmorty.nav.ui.NavActivity
import com.saam.rickandmorty.core.scope.ActivityScope
import com.saam.rickandmorty.episodeslist.di.EpisodesFragmentProvider
import com.saam.rickandmorty.locationslist.di.LocationsFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ActivityScope
    @ContributesAndroidInjector(modules = [
        NavModule::class,
        CharactersFragmentProvider::class,
        LocationsFragmentProvider::class,
        EpisodesFragmentProvider::class
    ])
    abstract fun bindsNavActivity(): NavActivity
}
