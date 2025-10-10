package com.example.mypokemonapplication.data.api

import com.example.mypokemonapplication.data.remote.dto.PokemonDetailsDto
import com.example.mypokemonapplication.data.remote.dto.PokemonListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(ApiRoutes.GET_POKEMON_LIST)
    suspend fun getPokemonData(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PokemonListDto

    @GET(ApiRoutes.GET_POKEMON_DETAILS)
    suspend fun getPokemonDetailsData(
        @Path("id") id: Int
    ): PokemonDetailsDto
}