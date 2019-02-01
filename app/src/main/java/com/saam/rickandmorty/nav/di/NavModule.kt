package com.saam.rickandmorty.nav.di

import com.saam.rickandmorty.nav.presentation.NavNavigator
import com.saam.rickandmorty.nav.ui.NavActivity
import com.saam.rickandmorty.core.scope.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class NavModule {
    @Provides
    @ActivityScope
    fun providesNavigator(activity: NavActivity): NavNavigator = NavNavigator(activity)
}
