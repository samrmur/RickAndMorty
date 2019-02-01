package com.saam.rickandmorty.core.module

import com.saam.rickandmorty.BuildConfig
import com.saam.rickandmorty.api.services.CharactersService
import com.saam.rickandmorty.api.services.EpisodesService
import com.saam.rickandmorty.api.services.LocationsService
import com.saam.rickandmorty.util.json.ApplicationJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
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
            .add(ApplicationJsonAdapterFactory.INSTANCE)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofitClient(client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BaseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
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