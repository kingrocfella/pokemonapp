package com.example.mypokemonapplication

import com.example.mypokemonapplication.data.models.ViewModelState
import com.example.mypokemonapplication.feature.pokemonlist.PokemonListViewModel
import com.example.mypokemonapplication.data.repository.PokemonUrlRepository
import com.example.mypokemonapplication.domain.entities.Pokemon
import com.example.mypokemonapplication.domain.entities.PokemonListResult
import com.example.mypokemonapplication.domain.usecase.GetPokemonListUseCase
import com.example.mypokemonapplication.services.NavigationService
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
    private lateinit var mockGetPokemonListUseCase: GetPokemonListUseCase

    private lateinit var pokemonUrlRepository: PokemonUrlRepository

    private lateinit var navigationService: NavigationService


    private lateinit var viewModel: PokemonListViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        pokemonUrlRepository = PokemonUrlRepository()
        navigationService = NavigationService()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `ViewModel should initialize with loading state`() {
        runBlocking {
            whenever(mockGetPokemonListUseCase.invoke(20, 0)).thenReturn(
                Result.success(PokemonListResult(0, null, null, emptyList()))
            )
        }

        viewModel = PokemonListViewModel(
            mockGetPokemonListUseCase, pokemonUrlRepository,
            navigationService = navigationService
        )

        val initialState = viewModel.getPokemonListState.value

        assertTrue("Should start in loading state", initialState.isLoading)
        assertFalse("Should not be in error state initially", initialState.isError)
        assertNull("Pokemon list should be null initially", initialState.data)
    }

    @Test
    fun `ViewModel should handle successful API response`() = runTest {

        val mockPokemonList = PokemonListResult(
            count = 2,
            next = null,
            previous = null,
            pokemons = listOf(
                Pokemon("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"),
                Pokemon("ivysaur", "https://pokeapi.co/api/v2/pokemon/2/")
            )
        )

        runBlocking {
            whenever(mockGetPokemonListUseCase.invoke(20, 0)).thenReturn(Result.success(mockPokemonList))
        }

        // Create ViewModel with mocked use case
        viewModel =
            PokemonListViewModel(mockGetPokemonListUseCase, pokemonUrlRepository, navigationService)

        advanceUntilIdle()

        val state = viewModel.getPokemonListState.value
        assertFalse("Should not be loading after success", state.isLoading)
        assertFalse("Should not be in error state", state.isError)
        assertNotNull("Pokemon list should not be null", state.data)
        assertEquals("Should have correct count", 2, state.data?.count)
        assertEquals("Should have 2 results", 2, state.data?.pokemons?.size)
    }

    @Test
    fun `ViewModel should handle empty Pokemon list`() = runTest {

        val emptyPokemonList = PokemonListResult(
            count = 0,
            next = null,
            previous = null,
            pokemons = emptyList()
        )

        runBlocking {
            whenever(mockGetPokemonListUseCase.invoke(20, 0)).thenReturn(Result.success(emptyPokemonList))
        }

        // Create ViewModel with mocked use case
        viewModel =
            PokemonListViewModel(mockGetPokemonListUseCase, pokemonUrlRepository, navigationService)

        advanceUntilIdle()

        val state = viewModel.getPokemonListState.value
        assertFalse("Should not be loading", state.isLoading)
        assertFalse("Should not be in error state", state.isError)
        assertNotNull("Pokemon list should not be null", state.data)
        assertEquals("Should have 0 count", 0, state.data?.count)
        assertTrue("Results should be empty", state.data?.pokemons?.isEmpty() == true)
    }

    @Test
    fun `ViewModel should handle pagination with next URL`() = runTest {

        val firstPageResponse = PokemonListResult(
            count = 4,
            next = "https://pokeapi.co/api/v2/pokemon?offset=20&limit=20",
            previous = null,
            pokemons = listOf(
                Pokemon("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"),
                Pokemon("ivysaur", "https://pokeapi.co/api/v2/pokemon/2/")
            )
        )

        runBlocking {
            whenever(mockGetPokemonListUseCase.invoke(20, 0)).thenReturn(Result.success(firstPageResponse))
        }

        // Create ViewModel with mocked use case
        viewModel =
            PokemonListViewModel(mockGetPokemonListUseCase, pokemonUrlRepository, navigationService)

        advanceUntilIdle()

        val state = viewModel.getPokemonListState.value
        assertFalse("Should not be loading", state.isLoading)
        assertFalse("Should not be in error state", state.isError)
        assertNotNull("Pokemon list should not be null", state.data)
        assertNotNull("Next URL should be available", state.data?.next)
        assertTrue("Should have next page", state.data?.next?.isNotEmpty() == true)
    }

    @Test
    fun `ViewModel should handle pagination with previous URL`() = runTest {

        val secondPageResponse = PokemonListResult(
            count = 4,
            next = null,
            previous = "https://pokeapi.co/api/v2/pokemon?offset=0&limit=20",
            pokemons = listOf(
                Pokemon("venusaur", "https://pokeapi.co/api/v2/pokemon/3/"),
                Pokemon("charmander", "https://pokeapi.co/api/v2/pokemon/4/")
            )
        )

        runBlocking {
            whenever(mockGetPokemonListUseCase.invoke(20, 0)).thenReturn(Result.success(secondPageResponse))
        }

        // Create ViewModel with mocked use case
        viewModel =
            PokemonListViewModel(mockGetPokemonListUseCase, pokemonUrlRepository, navigationService)

        advanceUntilIdle()

        val state = viewModel.getPokemonListState.value
        assertFalse("Should not be loading", state.isLoading)
        assertFalse("Should not be in error state", state.isError)
        assertNotNull("Pokemon list should not be null", state.data)
        assertNotNull("Previous URL should be available", state.data?.previous)
        assertTrue("Should have previous page", state.data?.previous?.isNotEmpty() == true)
    }

}
