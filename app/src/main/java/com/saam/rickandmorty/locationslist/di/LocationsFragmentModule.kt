package com.saam.rickandmorty.locationslist.di

import androidx.lifecycle.Lifecycle
import com.saam.rickandmorty.locationslist.ui.LocationsFragment
import com.saam.rickandmorty.core.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class LocationsFragmentModule {
    @FragmentScope
    @Provides
    fun providesLifecycle(fragment: LocationsFragment): Lifecycle = fragment.lifecycle
}