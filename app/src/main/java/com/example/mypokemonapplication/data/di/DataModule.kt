package com.example.mypokemonapplication.data.di

import com.example.mypokemonapplication.data.repository.PokemonRepositoryImpl
import com.example.mypokemonapplication.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    
    /**
     * Binds the PokemonRepositoryImpl to PokemonRepository interface
     * Hilt will automatically inject PokemonRepositoryImpl when PokemonRepository is requested
     */
    @Binds
    @Singleton
    abstract fun bindPokemonRepository(
        pokemonRepositoryImpl: PokemonRepositoryImpl
    ): PokemonRepository
}


