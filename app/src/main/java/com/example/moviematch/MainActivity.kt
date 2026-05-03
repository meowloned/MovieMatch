package com.example.moviematch
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.moviematch.data.repositoryImpl.AuthRepositoryImpl
import com.example.moviematch.domain.usecases.GetCurrentIdUseCase
import com.example.moviematch.domain.usecases.LoginUseCase
import com.example.moviematch.domain.usecases.LogoutUseCase
import com.example.moviematch.domain.usecases.RegisterUseCase
import com.example.moviematch.presentation.ViewModel.AuthViewModel
import com.example.moviematch.presentation.navigation.AppNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authRepository = AuthRepositoryImpl()

        val authViewModel = AuthViewModel(
            loginUseCase = LoginUseCase(authRepository),
            registerUseCase = RegisterUseCase(authRepository),
            getCurrentIdUseCase = GetCurrentIdUseCase(authRepository),
            logoutUseCase = LogoutUseCase(authRepository)
        )

        setContent {
            AppNavGraph(
                authViewModel = authViewModel
            )
        }
    }
}