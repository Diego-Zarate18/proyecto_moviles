package com.diego.proyecto.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.diego.proyecto.screens.auth.ResetPasswordScreen
import com.diego.proyecto.screens.auth.PasswordScreen
import com.diego.proyecto.screens.auth.RegisterScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.RESET_PASSWORD_SCREEN
    ) {

        composable(route = ScreenRoutes.RESET_PASSWORD_SCREEN) {
            ResetPasswordScreen()
        }

        composable(route = ScreenRoutes.REGISTER_SCREEN) {
            RegisterScreen(navController = navController)
        }

        composable(route = ScreenRoutes.PASSWORD_SCREEN){
            PasswordScreen(navController = navController)
        }
    }
}