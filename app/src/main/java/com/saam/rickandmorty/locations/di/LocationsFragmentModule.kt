package com.saam.rickandmorty.locations.di

import androidx.lifecycle.Lifecycle
import com.saam.rickandmorty.locations.ui.LocationsFragment
import com.saam.rickandmorty.core.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class LocationsFragmentModule {
    @FragmentScope
    @Provides
    fun providesLifecycle(fragment: LocationsFragment): Lifecycle = fragment.lifecycle
}