package com.saam.rickandmorty.locationslist.di

import com.saam.rickandmorty.locationslist.ui.LocationsFragment
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
