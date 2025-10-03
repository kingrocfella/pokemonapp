package com.example.mypokemonapplication

import com.example.mypokemonapplication.common.utils.Utils
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock


class UtilsTest {

    @Mock
    private lateinit var utils: Utils

    @Before
    fun setUp() {
        utils = Utils
    }

    @Test
    fun `Utils should extract Pokemon ID from valid URL`() {
        val validUrl = "https://pokeapi.co/api/v2/pokemon/25/"
        val pokemonId = utils.getPokemonIdFromUrl(validUrl)

        assertNotNull("Pokemon ID should not be null", pokemonId)
        assertEquals("Should extract correct Pokemon ID", 25, pokemonId)
    }

    @Test
    fun `Utils should return null for invalid URL format`() {
        val invalidUrl = "https://pokeapi.co/api/v2/pokemon/invalid/"
        val pokemonId = utils.getPokemonIdFromUrl(invalidUrl)

        assertNull("Pokemon ID should be null for invalid URL", pokemonId)
    }

    @Test
    fun `Utils should handle empty URL`() {
        val emptyUrl = ""
        val pokemonId = utils.getPokemonIdFromUrl(emptyUrl)

        assertNull("Pokemon ID should be null for empty URL", pokemonId)
    }

    @Test
    fun `Utils should capitalize first letter of Pokemon name`() {
        val pokemonName = "bulbasaur"
        val capitalizedName = utils.capitalizeFirstLetter(pokemonName)

        assertEquals("Should capitalize first letter", "Bulbasaur", capitalizedName)
    }

    @Test
    fun `Utils should handle null Pokemon name`() {
        val pokemonName: String? = null
        val capitalizedName = utils.capitalizeFirstLetter(pokemonName)

        assertEquals("Should return dash for null name", "-", capitalizedName)
    }
}
