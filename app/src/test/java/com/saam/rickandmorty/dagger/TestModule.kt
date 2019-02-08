package com.saam.rickandmorty.dagger

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.saam.rickandmorty.api.adapters.EpisodeModelAdapter
import com.saam.rickandmorty.api.services.CharactersService
import com.saam.rickandmorty.api.services.EpisodesService
import com.saam.rickandmorty.api.services.LocationsService
import com.saam.rickandmorty.util.json.ApplicationJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class TestModule {
    @Singleton
    @Provides
    fun providesMockServer(): MockWebServer = MockWebServer()

    @Singleton
    @Provides
    fun providesHttpLoggerIntercepor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BASIC)
            .setLevel(HttpLoggingInterceptor.Level.BODY)
            .setLevel(HttpLoggingInterceptor.Level.HEADERS)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(logger: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(logger)
            .build()
    }

    @Singleton
    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .add(EpisodeModelAdapter())
            .add(ApplicationJsonAdapterFactory.INSTANCE)
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofitClient(client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://localhost:8080/")
            .addConverterFactory(MoshiConverterFactory.create(moshi).failOnUnknown())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun providesCharactersService(retrofit: Retrofit): CharactersService {
        return retrofit.create(CharactersService::class.java)
    }

    @Singleton
    @Provides
    fun providesLocationsService(retrofit: Retrofit): LocationsService {
        return retrofit.create(LocationsService::class.java)
    }

    @Singleton
    @Provides
    fun providesEpisodesService(retrofit: Retrofit): EpisodesService {
        return retrofit.create(EpisodesService::class.java)
    }
}