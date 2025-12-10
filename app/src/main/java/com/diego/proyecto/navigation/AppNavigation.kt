package com.diego.proyecto.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.diego.proyecto.screens.auth.LoginScreen
import com.diego.proyecto.screens.auth.PasswordScreen
import com.diego.proyecto.screens.auth.RegisterScreen
import com.diego.proyecto.screens.practice.NewPracticeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.NEW_PRACTICE_SCREEN
    ) {

        composable(route = ScreenRoutes.LOGIN_SCREEN) {
            LoginScreen(navController = navController)
        }

        composable(route = ScreenRoutes.REGISTER_SCREEN) {
            RegisterScreen(navController = navController)
        }

        composable(route = ScreenRoutes.PASSWORD_SCREEN){
            PasswordScreen(navController = navController)
        }

        composable(route = ScreenRoutes.NEW_PRACTICE_SCREEN) {
            NewPracticeScreen()
        }
    }
}
