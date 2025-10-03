package com.example.mypokemonapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mypokemonapplication.common.screens.ScreenNames
import com.example.mypokemonapplication.services.NavigationService
import com.example.mypokemonapplication.feature.pokemondetails.PokemonDetailsScreen
import com.example.mypokemonapplication.feature.pokemonlist.PokemonListScreen
import com.example.mypokemonapplication.ui.theme.MypokemonapplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationService: NavigationService

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MypokemonapplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    StartUpApp(navigationService)
                }
            }
        }
    }
}

@Composable
fun StartUpApp(navigationService: NavigationService) {
    val navController = rememberNavController()

    // Set the navController in the service so it can be used from ViewModels
    LaunchedEffect(navController) {
        navigationService.setNavController(navController)
    }

    NavHost(navController = navController, startDestination = ScreenNames.HOME) {
        composable(ScreenNames.HOME) { PokemonListScreen() }
        composable(ScreenNames.DETAILS) { PokemonDetailsScreen() }
    }
}
