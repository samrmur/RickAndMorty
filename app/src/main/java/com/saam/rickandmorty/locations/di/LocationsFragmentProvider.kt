package com.saam.rickandmorty.locations.di

import com.saam.rickandmorty.locations.di.LocationsFragmentModule
import com.saam.rickandmorty.locations.ui.LocationsFragment
import com.saam.rickandmorty.core.scope.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LocationsFragmentProvider {
    @FragmentScope
    @ContributesAndroidInjector(modules = [
        LocationsFragmentModule::class
    ])
    abstract fun providesLocationsFragment(): LocationsFragment
}
