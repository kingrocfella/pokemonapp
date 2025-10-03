package com.example.mypokemonapplication.data.api

import com.example.mypokemonapplication.data.models.PokemonDetails
import com.example.mypokemonapplication.data.models.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(ApiRoutes.GET_POKEMON_LIST)
    suspend fun getPokemonData(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PokemonList

    @GET(ApiRoutes.GET_POKEMON_DETAILS)
    suspend fun getPokemonDetailsData(
        @Path("id") id: Int
    ): PokemonDetails
}