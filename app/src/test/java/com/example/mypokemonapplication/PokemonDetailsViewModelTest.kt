package com.example.mypokemonapplication

import com.example.mypokemonapplication.domain.entities.PokemonDetail
import com.example.mypokemonapplication.feature.pokemondetails.PokemonDetailsViewModel
import com.example.mypokemonapplication.data.repository.PokemonUrlRepository
import com.example.mypokemonapplication.domain.usecase.GetPokemonDetailsUseCase
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
    private lateinit var mockGetPokemonDetailsUseCase: GetPokemonDetailsUseCase

    private lateinit var pokemonUrlRepository: PokemonUrlRepository

    private lateinit var viewModel: PokemonDetailsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        pokemonUrlRepository = PokemonUrlRepository()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `ViewModel should initialize with loading state`() {

        runBlocking {
            whenever(mockGetPokemonDetailsUseCase.invoke(25)).thenReturn(
                Result.success(createMockPokemonDetails())
            )
        }

        viewModel = PokemonDetailsViewModel(mockGetPokemonDetailsUseCase, pokemonUrlRepository)
        val initialState = viewModel.getPokemonDetailsState.value

        assertTrue("Should start in loading state", initialState.isLoading)
        assertFalse("Should not be in error state initially", initialState.isError)
        assertNull("Pokemon details should be null initially", initialState.data)
    }

    @Test
    fun `ViewModel should handle successful API response`() = runTest {

        val testUrl = "https://pokeapi.co/api/v2/pokemon/25/"
        val mockPokemonDetails = createMockPokemonDetails()

        runBlocking {
            whenever(mockGetPokemonDetailsUseCase.invoke(25)).thenReturn(Result.success(mockPokemonDetails))
        }

        viewModel = PokemonDetailsViewModel(mockGetPokemonDetailsUseCase, pokemonUrlRepository)
        pokemonUrlRepository.setSelectedPokemonUrl(testUrl)
        advanceUntilIdle()

        val state = viewModel.getPokemonDetailsState.value
        assertFalse("Should not be loading after success", state.isLoading)
        assertFalse("Should not be in error state", state.isError)
        assertNotNull("Pokemon details should not be null", state.data)
        assertEquals("Should have correct Pokemon ID", 25, state.data?.id)
        assertEquals("Should have correct Pokemon name", "pikachu", state.data?.name)
    }

    @Test
    fun `ViewModel should handle invalid Pokemon URL`() = runTest {

        val invalidUrl = "https://pokeapi.co/api/v2/pokemon/invalid/"

        val mockRepository = PokemonUrlRepository()
        viewModel = PokemonDetailsViewModel(mockGetPokemonDetailsUseCase, mockRepository)
        mockRepository.setSelectedPokemonUrl(invalidUrl)
        advanceUntilIdle()

        val state = viewModel.getPokemonDetailsState.value
        assertFalse("Should not be loading after error", state.isLoading)
        assertTrue("Should be in error state", state.isError)
        assertNull("Pokemon details should be null", state.data)
        assertNotNull("Error message should not be null", state.errorMessage)
    }

    @Test
    fun `ViewModel should handle empty Pokemon URL`() = runTest {

        val emptyUrl = ""

        val mockRepository = PokemonUrlRepository()
        viewModel = PokemonDetailsViewModel(mockGetPokemonDetailsUseCase, mockRepository)
        mockRepository.setSelectedPokemonUrl(emptyUrl)
        advanceUntilIdle()

        val state = viewModel.getPokemonDetailsState.value
        assertFalse("Should not be loading after error", state.isLoading)
        assertTrue("Should be in error state", state.isError)
        assertNull("Pokemon details should be null", state.data)
        assertNotNull("Error message should not be null", state.errorMessage)
    }

    // Helper function to create mock PokemonDetail domain entity
    private fun createMockPokemonDetails(
        id: Int = 25,
        name: String = "pikachu",
        height: Int = 4,
        imageUrl: String = "https://pokeapi.co/api/v2/pokemon/25/sprites/front_default.png"
    ): PokemonDetail {
        return PokemonDetail(
            id = id,
            name = name,
            height = height,
            imageUrl = imageUrl
        )
    }
}
