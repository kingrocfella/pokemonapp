package com.example.mypokemonapplication.common.api

import com.example.mypokemonapplication.pokemondetails.PokemonDetails
import com.example.mypokemonapplication.pokemonlist.PokemonList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private val retrofit = Retrofit.Builder()
    .baseUrl(ApiRoutes.POKEMON_BASE_URL.url)
    .addConverterFactory(GsonConverterFactory.create()).build()

val makeAPICall: ApiService = retrofit.create(ApiService::class.java)

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemonData(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PokemonList

    @GET("pokemon/{id}")
    suspend fun getPokemonDetailsData(
        @Path("id") id: Int
    ): PokemonDetails
}