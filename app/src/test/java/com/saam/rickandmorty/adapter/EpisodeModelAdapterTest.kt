package com.saam.rickandmorty.adapter

import com.saam.rickandmorty.api.adapters.EpisodeModelAdapter
import com.squareup.moshi.JsonReader
import okio.Okio
import org.junit.Test

class EpisodeModelAdapterTest {
    @Test
    fun adapterTest() {
        val classLoader = this.javaClass.classLoader
        val inputStream = classLoader!!.getResourceAsStream("json/episode.json")
        val adapter = EpisodeModelAdapter()
        val episode = adapter.fromJson(JsonReader.of(Okio.buffer(Okio.source(inputStream))))
        assert(episode?.id == 1)
    }
}