package com.example.moviematch
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.moviematch.data.local.FilmJsonDataSource
import com.example.moviematch.data.repositoryImpl.AuthRepositoryImpl
import com.example.moviematch.data.repositoryImpl.FilmsRepositoryImpl
import com.example.moviematch.domain.repository.FilmsRepository
import com.example.moviematch.domain.usecases.GetCurrentIdUseCase
import com.example.moviematch.domain.usecases.GetFilmsUseCase
import com.example.moviematch.domain.usecases.LoginUseCase
import com.example.moviematch.domain.usecases.LogoutUseCase
import com.example.moviematch.domain.usecases.RegisterUseCase
import com.example.moviematch.presentation.ViewModel.AuthViewModel
import com.example.moviematch.presentation.ViewModel.FilmsViewModel
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
        val filmJsonDataSource = FilmJsonDataSource(this)

        val filmsRepository = FilmsRepositoryImpl(
            dataSource = filmJsonDataSource
        )
        val filmsViewModel = FilmsViewModel(getFilmsUseCase = GetFilmsUseCase(filmsRepository))
        setContent {
            AppNavGraph(
                authViewModel = authViewModel,
                filmsViewModel = filmsViewModel
            )
        }
    }
}