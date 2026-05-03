package com.example.moviematch.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviematch.presentation.UI.screens.auth.LoginScreen
import com.example.moviematch.presentation.UI.screens.auth.RegisterScreen
import com.example.moviematch.presentation.ViewModel.AuthViewModel


@Composable
fun AppNavGraph(
    authViewModel: AuthViewModel
) {
    val navController = rememberNavController()
    val state = authViewModel.state
    LaunchedEffect(Unit) {
        authViewModel.checkCurrentUser()
    }
    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) {
            navController.navigate("main") {
                popUpTo("login") {
                    inclusive = true
                }
            }
        }
    }
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                authViewModel = authViewModel,
                onRegisterClick = {
                    navController.navigate("register")
                }
            )
        }

        composable("register") {
            RegisterScreen(
                authViewModel = authViewModel,
                onLoginClick = {
                    navController.navigate("login")
                }
            )
        }

        composable("main") {
            Text("Главный экран")
        }
    }
}