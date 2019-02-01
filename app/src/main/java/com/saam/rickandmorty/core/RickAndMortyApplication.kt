package com.saam.rickandmorty.core

import com.saam.rickandmorty.BuildConfig
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import com.saam.rickandmorty.core.component.DaggerApplicationComponent
import timber.log.Timber
import timber.log.Timber.DebugTree



class RickAndMortyApplication: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = DaggerApplicationComponent.builder().create(this)

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Timber.plant(DebugTree())
    }
}


