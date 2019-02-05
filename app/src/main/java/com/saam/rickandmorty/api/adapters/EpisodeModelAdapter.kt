package com.saam.rickandmorty.api.adapters

import com.saam.rickandmorty.api.models.Episode
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import java.io.IOException

/**
 * Type Adapter for changing creating an Episode Model Class
 * @author Samer Alabi
 */
class EpisodeModelAdapter {
    private var id: Int = 0
    private lateinit var name: String
    private lateinit var airDate: String
    private var season: Int = 0
    private var number: Int = 0
    private lateinit var characters: MutableList<String>
    private lateinit var url: String
    private lateinit var created: String

    /**
     * Takes a JSON reader and creates an Episode model from its values
     * @return Episode?
     */
    @FromJson
    @Throws(IOException::class)
    fun fromJson(reader: JsonReader): Episode? {
        reader.beginObject()
        while (reader.hasNext()) {
            val nameReader = reader.nextName()

            when (nameReader) {
                "id" -> id = reader.nextInt()
                "name" -> name = reader.nextString()
                "air_date" -> airDate = reader.nextString()
                "episode" -> {
                    val episode = reader.nextString()
                    season = getSeason(episode)
                    number = getNumber(episode)
                }
                "characters" -> {
                    characters = mutableListOf()

                    reader.beginArray()
                    while (reader.hasNext())
                        characters.add(reader.nextString())
                    reader.endArray()
                }
                "url" -> url = reader.nextString()
                "created" -> created = reader.nextString()
            }
        }
        reader.endObject()

        return Episode(id, name, airDate, season, number, characters, url, created)
    }

    /**
     * Removes leading zeroes from a string
     * @param leading String
     * @return String
     */
    private fun removeLeadingZeroes(leading: String): String = if (leading.startsWith("0")) removeLeadingZeroes(leading.substring(1)) else leading

    /**
     * Gets the season from from an episode String
     * @return Int
     */
    private fun getSeason(episode: String): Int = removeLeadingZeroes(episode.substring(1, 3)).toInt()

    /**
     * Gets the episode number from from an episode String
     * @return Int
     */
    private fun getNumber(episode: String): Int = removeLeadingZeroes(episode.substring(4, 6)).toInt()
}