package com.example.moviematch.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviematch.presentation.UI.screens.auth.LoginScreen
import com.example.moviematch.presentation.UI.screens.auth.RegisterScreen
import com.example.moviematch.presentation.UI.screens.main.FavouritesScreen
import com.example.moviematch.presentation.UI.screens.main.FriendsScreen
import com.example.moviematch.presentation.UI.screens.main.MainScreen
import com.example.moviematch.presentation.UI.screens.main.ProfileScreen
import com.example.moviematch.presentation.ViewModel.AuthViewModel
import com.example.moviematch.presentation.ViewModel.FavouritesViewModel
import com.example.moviematch.presentation.ViewModel.FilmsViewModel
import com.example.moviematch.presentation.ViewModel.FriendsViewModel
import com.example.moviematch.presentation.ViewModel.ProfileViewModel


@Composable
fun AppNavGraph(
    authViewModel: AuthViewModel,
    filmsViewModel: FilmsViewModel,
    favouritesViewModel: FavouritesViewModel,
    friendsViewModel: FriendsViewModel,
    profileViewModel: ProfileViewModel
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
        startDestination = if (state.isLoggedIn) "main" else "login"
    ) {
        composable("login") {
            LoginScreen(
                authViewModel = authViewModel,
                onRegisterClick = {
                    navController.navigate("register")
                },
                onMainClick = {navController.navigate("main")}
                )
        }

        composable("register") {
            RegisterScreen(
                authViewModel = authViewModel,
                onLoginClick = {
                    navController.navigate("login")
                },
                onMainClick = {navController.navigate("main")}
                )
        }

        composable("main") {
            MainScreen(
                filmsViewModel = filmsViewModel,
                onProfileClick = {navController.navigate("profile")},
                onMainClick = {navController.navigate("main")},
                onFavClick = {navController.navigate("favourites")},
                onFriendsClick = {navController.navigate("friends")}
            )
        }

        composable("favourites"){
            FavouritesScreen(
                favouritesViewModel = favouritesViewModel,
                filmsViewModel = filmsViewModel,
                onProfileClick = {navController.navigate("profile")},
                onMainClick = {navController.navigate("main")},
                onFavClick = {navController.navigate("favourites")},
                onFriendsClick = {navController.navigate("friends")}
                )
        }
        composable("friends"){
            FriendsScreen(
                friendsViewModel = friendsViewModel,
                onProfileClick = {navController.navigate("profile")},
                onMainClick = {navController.navigate("main")},
                onFavClick = {navController.navigate("favourites")},
                onFriendsClick = {navController.navigate("friends")}
            )
        }

        composable("profile"){
            ProfileScreen(
                profileViewModel = profileViewModel,
                onProfileClick = {navController.navigate("profile")},
                onMainClick = {navController.navigate("main")},
                onFavClick = {navController.navigate("favourites")},
                onFriendsClick = {navController.navigate("friends")},
                onLogClick = {navController.navigate("login") {
                    popUpTo(0)
                }}
            )
        }
    }
}