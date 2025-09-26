package com.example.mypokemonapplication

import com.example.mypokemonapplication.pokemondetails.PokemonDetails
import com.example.mypokemonapplication.pokemondetails.PokemonDetailsViewModel
import com.example.mypokemonapplication.pokemondetails.PokemonSpriteDetails
import com.example.mypokemonapplication.common.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import kotlinx.coroutines.runBlocking


@ExperimentalCoroutinesApi
class PokemonDetailsViewModelTest {

    @Mock
    private lateinit var mockApiService: ApiService

    private lateinit var viewModel: PokemonDetailsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `ViewModel should initialize with loading state`() {

        val testUrl = "https://pokeapi.co/api/v2/pokemon/25/"
        runBlocking {
            whenever(mockApiService.getPokemonDetailsData(25)).thenReturn(
                createMockPokemonDetails()
            )
        }

        viewModel = PokemonDetailsViewModel(testUrl, mockApiService)
        val initialState = viewModel.getPokemonDetailsState

        assertTrue("Should start in loading state", initialState.isLoading)
        assertFalse("Should not be in error state initially", initialState.isError)
        assertNull("Pokemon details should be null initially", initialState.pokemonDetailsData)
    }

    @Test
    fun `ViewModel should handle successful API response`() = runTest {

        val testUrl = "https://pokeapi.co/api/v2/pokemon/25/"
        val mockPokemonDetails = createMockPokemonDetails()

        runBlocking {
            whenever(mockApiService.getPokemonDetailsData(25)).thenReturn(mockPokemonDetails)
        }

        viewModel = PokemonDetailsViewModel(testUrl, mockApiService)
        advanceUntilIdle()

        val state = viewModel.getPokemonDetailsState
        assertFalse("Should not be loading after success", state.isLoading)
        assertFalse("Should not be in error state", state.isError)
        assertNotNull("Pokemon details should not be null", state.pokemonDetailsData)
        assertEquals("Should have correct Pokemon ID", 25, state.pokemonDetailsData?.id)
        assertEquals("Should have correct Pokemon name", "pikachu", state.pokemonDetailsData?.name)
    }

    @Test
    fun `ViewModel should handle invalid Pokemon URL`() = runTest {

        val invalidUrl = "https://pokeapi.co/api/v2/pokemon/invalid/"

        viewModel = PokemonDetailsViewModel(invalidUrl, mockApiService)
        advanceUntilIdle()

        val state = viewModel.getPokemonDetailsState
        assertFalse("Should not be loading after invalid URL", state.isLoading)
        assertTrue("Should be in error state", state.isError)
        assertNull("Pokemon details should be null", state.pokemonDetailsData)
    }

    @Test
    fun `ViewModel should handle empty Pokemon URL`() = runTest {

        val emptyUrl = ""

        viewModel = PokemonDetailsViewModel(emptyUrl, mockApiService)
        advanceUntilIdle()

        val state = viewModel.getPokemonDetailsState
        assertFalse("Should not be loading after empty URL", state.isLoading)
        assertTrue("Should be in error state", state.isError)
        assertNull("Pokemon details should be null", state.pokemonDetailsData)
    }

    // Helper function to create mock PokemonDetails data class
    private fun createMockPokemonDetails(
        id: Int = 25,
        name: String = "pikachu",
        height: Int = 4,
        spriteUrl: String = "https://pokeapi.co/api/v2/pokemon/25/sprites/front_default.png"
    ): PokemonDetails {
        return PokemonDetails(
            id = id,
            name = name,
            height = height,
            sprites = PokemonSpriteDetails(front_default = spriteUrl)
        )
    }
}
