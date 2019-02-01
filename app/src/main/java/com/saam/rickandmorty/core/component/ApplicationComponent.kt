package com.saam.rickandmorty.core.component

import com.saam.rickandmorty.core.RickAndMortyApplication
import com.saam.rickandmorty.core.module.ActivityBuilder
import com.saam.rickandmorty.core.module.ApplicationModule
import com.saam.rickandmorty.core.module.NetworkModule
import com.saam.rickandmorty.core.module.viewmodel.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    ActivityBuilder::class,
    NetworkModule::class,
    ViewModelModule::class
])
interface ApplicationComponent: AndroidInjector<RickAndMortyApplication> {
    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<RickAndMortyApplication>()
}