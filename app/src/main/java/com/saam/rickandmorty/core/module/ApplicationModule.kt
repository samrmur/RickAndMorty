package com.saam.rickandmorty.core.module

import android.content.Context
import com.saam.rickandmorty.core.RickAndMortyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {
    @Singleton
    @Provides
    fun providesApplication(application: RickAndMortyApplication): RickAndMortyApplication = application

    @Singleton
    @Provides
    fun providesApplicationContext(application: RickAndMortyApplication): Context = application.applicationContext
}