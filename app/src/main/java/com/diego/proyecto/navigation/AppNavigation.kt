package com.diego.proyecto.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.diego.proyecto.screens.auth.PasswordScreen
import com.diego.proyecto.screens.auth.RegisterScreen
import com.diego.proyecto.screens.auth.ResetPasswordScreen
import com.diego.proyecto.screens.home.HomeScreen
import com.diego.proyecto.screens.practice.NewPracticeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.LOGIN_SCREEN
    ) {

        // Login is now LoginScreen (was PasswordScreen, but we renamed the Composable if not the file, or should use PasswordScreen as Login)
        // Wait, the user renamed the file or just the function?
        // Let's check PasswordScreen.kt content again.
        // It has @Composable fun LoginScreen(navController: NavController).
        // So I should import LoginScreen from com.diego.proyecto.screens.auth.PasswordScreen?
        // Or did I rename the file?
        // I haven't renamed the file "PasswordScreen.kt", but I changed the function inside it to "LoginScreen".
        // The user says "el login parace llamarse PasswordScreen".
        // If the file is PasswordScreen.kt and function is LoginScreen, the import should be:
        // import com.diego.proyecto.screens.auth.LoginScreen (if it's in that package)
        
        composable(route = ScreenRoutes.LOGIN_SCREEN) {
            PasswordScreen(navController = navController)
        }

        composable(route = ScreenRoutes.REGISTER_SCREEN) {
            RegisterScreen(navController = navController)
        }

        // Changed PASSWORD_SCREEN to point to ResetPasswordScreen for logic
        composable(route = ScreenRoutes.PASSWORD_SCREEN){
            ResetPasswordScreen(navController = navController)
        }

        composable(route = ScreenRoutes.NEW_PRACTICE_SCREEN) {
            NewPracticeScreen(navController = navController)
        }

        composable(route = ScreenRoutes.HOME_SCREEN) {
            HomeScreen(navController = navController)
        }
    }
}
