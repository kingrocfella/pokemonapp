package com.example.mypokemonapplication.services

import androidx.navigation.NavController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationService @Inject constructor() {

    private lateinit var _navController: NavController

    fun setNavController(navController: NavController) {
        _navController = navController
    }

    fun navigateTo(route: String) {
        _navController.navigate(route)
    }
}