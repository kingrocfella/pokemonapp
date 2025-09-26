package com.example.mypokemonapplication

import com.example.mypokemonapplication.pokemonlist.PokemonList
import com.example.mypokemonapplication.pokemonlist.PokemonListResult
import com.example.mypokemonapplication.pokemonlist.PokemonListViewModel
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
class PokemonListViewModelTest {

    @Mock
    private lateinit var mockApiService: ApiService

    private lateinit var viewModel: PokemonListViewModel
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
        runBlocking {
            whenever(mockApiService.getPokemonData(20, 0)).thenReturn(
                PokemonList(0, null, null, emptyList())
            )
        }

        viewModel = PokemonListViewModel(mockApiService)
        val initialState = viewModel.getPokemonListState

        assertTrue("Should start in loading state", initialState.isLoading)
        assertFalse("Should not be in error state initially", initialState.isError)
        assertNull("Pokemon list should be null initially", initialState.pokemonList)
    }

    @Test
    fun `ViewModel should handle successful API response`() = runTest {

        val mockPokemonList = PokemonList(
            count = 2,
            next = null,
            previous = null,
            results = listOf(
                PokemonListResult("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"),
                PokemonListResult("ivysaur", "https://pokeapi.co/api/v2/pokemon/2/")
            )
        )

        runBlocking {
            whenever(mockApiService.getPokemonData(20, 0)).thenReturn(mockPokemonList)
        }

        // Create ViewModel with mocked API service
        viewModel = PokemonListViewModel(mockApiService)

        advanceUntilIdle()

        val state = viewModel.getPokemonListState
        assertFalse("Should not be loading after success", state.isLoading)
        assertFalse("Should not be in error state", state.isError)
        assertNotNull("Pokemon list should not be null", state.pokemonList)
        assertEquals("Should have correct count", 2, state.pokemonList?.count)
        assertEquals("Should have 2 results", 2, state.pokemonList?.results?.size)
    }

    @Test
    fun `ViewModel should handle empty Pokemon list`() = runTest {

        val emptyPokemonList = PokemonList(
            count = 0,
            next = null,
            previous = null,
            results = emptyList()
        )

        runBlocking {
            whenever(mockApiService.getPokemonData(20, 0)).thenReturn(emptyPokemonList)
        }

        // Create ViewModel with mocked API service
        viewModel = PokemonListViewModel(mockApiService)

        advanceUntilIdle()

        val state = viewModel.getPokemonListState
        assertFalse("Should not be loading", state.isLoading)
        assertFalse("Should not be in error state", state.isError)
        assertNotNull("Pokemon list should not be null", state.pokemonList)
        assertEquals("Should have 0 count", 0, state.pokemonList?.count)
        assertTrue("Results should be empty", state.pokemonList?.results?.isEmpty() == true)
    }

    @Test
    fun `ViewModel should handle pagination with next URL`() = runTest {

        val firstPageResponse = PokemonList(
            count = 4,
            next = "https://pokeapi.co/api/v2/pokemon?offset=20&limit=20",
            previous = null,
            results = listOf(
                PokemonListResult("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"),
                PokemonListResult("ivysaur", "https://pokeapi.co/api/v2/pokemon/2/")
            )
        )

        runBlocking {
            whenever(mockApiService.getPokemonData(20, 0)).thenReturn(firstPageResponse)
        }

        // Create ViewModel with mocked API service
        viewModel = PokemonListViewModel(mockApiService)

        advanceUntilIdle()

        val state = viewModel.getPokemonListState
        assertNotNull("Next URL should be available", state.pokemonList?.next)
        assertTrue("Should have next page", state.pokemonList?.next?.isNotEmpty() == true)
    }

    @Test
    fun `ViewModel should handle pagination with previous URL`() = runTest {

        val secondPageResponse = PokemonList(
            count = 4,
            next = null,
            previous = "https://pokeapi.co/api/v2/pokemon?offset=0&limit=20",
            results = listOf(
                PokemonListResult("venusaur", "https://pokeapi.co/api/v2/pokemon/3/"),
                PokemonListResult("charmander", "https://pokeapi.co/api/v2/pokemon/4/")
            )
        )

        runBlocking {
            whenever(mockApiService.getPokemonData(20, 0)).thenReturn(secondPageResponse)
        }

        // Create ViewModel with mocked API service
        viewModel = PokemonListViewModel(mockApiService)

        advanceUntilIdle()

        val state = viewModel.getPokemonListState
        assertNotNull("Previous URL should be available", state.pokemonList?.previous)
        assertTrue("Should have previous page", state.pokemonList?.previous?.isNotEmpty() == true)
    }

}
