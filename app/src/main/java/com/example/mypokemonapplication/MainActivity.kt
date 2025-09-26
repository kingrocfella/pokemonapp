package com.example.mypokemonapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mypokemonapplication.common.screens.ScreenNames
import com.example.mypokemonapplication.pokemondetails.PokemonDetailsScreen
import com.example.mypokemonapplication.pokemonlist.PokemonListScreen
import com.example.mypokemonapplication.ui.theme.MypokemonapplicationTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MypokemonapplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    StartUpApp()
                }
            }
        }
    }
}


@Composable
fun StartUpApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenNames.HOME.route) {
        composable(ScreenNames.HOME.route) {
            PokemonListScreen { detailUrl ->
                // Store the URL in the current back stack entry before navigating
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("detailUrl", detailUrl)

                navController.navigate(ScreenNames.DETAILS.route)
            }
        }
        composable(ScreenNames.DETAILS.route) { backStackEntry ->
            val context = LocalContext.current
            val detailUrl: String? = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<String>("detailUrl")

            if (detailUrl != null) {
                PokemonDetailsScreen(detailUrl)
            } else {
                // Only show toast if we're actually on the pokemon details screen and not navigating back
                if (navController.currentBackStackEntry?.destination?.route == ScreenNames.DETAILS.route) {
                    Toast.makeText(context, "No Pokemon details found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}