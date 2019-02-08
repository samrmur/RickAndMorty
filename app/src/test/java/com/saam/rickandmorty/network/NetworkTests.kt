package com.saam.rickandmorty.network

import com.saam.rickandmorty.api.models.Character
import com.saam.rickandmorty.api.models.Episode
import com.saam.rickandmorty.api.models.Location
import com.saam.rickandmorty.api.services.CharactersService
import com.saam.rickandmorty.api.services.EpisodesService
import com.saam.rickandmorty.api.services.LocationsService
import com.saam.rickandmorty.dagger.DaggerTestComponent
import com.saam.rickandmorty.dagger.TestComponent
import org.junit.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File
import javax.inject.Inject

class NetworkTests {
    private val testComponent: TestComponent = DaggerTestComponent.builder().build()

    @Inject lateinit var server: MockWebServer
    @Inject lateinit var charactersService: CharactersService
    @Inject lateinit var episodesService: EpisodesService
    @Inject lateinit var locationsService: LocationsService

    @Before
    fun setup() {
        testComponent.inject(this)
        server.start(8080)
    }

    @Test
    fun charactersTest() {
        enqueueRequest("json/characters.json")
        var character: Character? = null

        runBlocking {
            try {
                character = charactersService.getCharactersByIdAsync("1,183").await()[0]
            } catch (err: Exception) {
                character = null
                System.out.println(err.message)
            }
        }

        assertEquals(1, character?.id)
        assertEquals("Rick Sanchez", character?.name)
        assertEquals("Alive", character?.status)
        assertEquals("Human", character?.species)
    }

    @Test
    fun episodesTest() {
        enqueueRequest("json/episodes.json")
        var episode: Episode? = null

        runBlocking {
            try {
                episode = episodesService.getEpisodesByIdAsync("10,28").await()[0]
            } catch (err: Exception) {
                episode = null
                System.out.println(err.message)
            }
        }

        assertEquals(10, episode?.id)
        assertEquals("Close Rick-counters of the Rick Kind", episode?.name)
        assertEquals("April 7, 2014", episode?.airDate)
    }

    @Test
    fun locationsTest() {
        enqueueRequest("json/locations.json")
        var location: Location? = null

        runBlocking {
            try {
                location = locationsService.getLocationsByIdAsync("10,28").await()[0]
            } catch (err: Exception) {
                location = null
                System.out.println(err.message)
            }
        }

        assertEquals(3, location?.id)
        assertEquals("Citadel of Ricks", location?.name)
        assertEquals("unknown", location?.dimension)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    private fun enqueueRequest(path: String) {
        val response = MockResponse()
        response.setResponseCode(200)
        response.setBody(getJson(path))
        server.enqueue(response)
    }

    private fun getJson(path : String) : String {
        val uri = this.javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}