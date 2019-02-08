package com.saam.rickandmorty.dagger

import com.saam.rickandmorty.network.NetworkTests
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    TestModule::class
])
interface TestComponent {
    fun inject(test: NetworkTests)
}