package com.example.moviematch
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.moviematch.data.local.FilmJsonDataSource
import com.example.moviematch.data.repositoryImpl.AuthRepositoryImpl
import com.example.moviematch.data.repositoryImpl.FavouriteFilmsRepositoryImpl
import com.example.moviematch.data.repositoryImpl.FilmsRepositoryImpl
import com.example.moviematch.data.repositoryImpl.FriendsRepositoryImpl
import com.example.moviematch.data.repositoryImpl.SessionRepositoryImpl
import com.example.moviematch.domain.repository.FavouriteFilmsRepository
import com.example.moviematch.domain.repository.FilmsRepository
import com.example.moviematch.domain.repository.SessionRepository
import com.example.moviematch.domain.usecases.AcceptRequestUseCase
import com.example.moviematch.domain.usecases.AddFavUseCase
import com.example.moviematch.domain.usecases.FinishSessionUseCase
import com.example.moviematch.domain.usecases.GetAllFriendsUseCase
import com.example.moviematch.domain.usecases.GetAllRequestsUseCase
import com.example.moviematch.domain.usecases.GetCurrentEmailUseCase
import com.example.moviematch.domain.usecases.GetCurrentIdUseCase
import com.example.moviematch.domain.usecases.GetFavsUseCase
import com.example.moviematch.domain.usecases.GetFilmsUseCase
import com.example.moviematch.domain.usecases.GetOrCreateSessionUseCase
import com.example.moviematch.domain.usecases.GetUserByIdUseCase
import com.example.moviematch.domain.usecases.LikeFilmSessionUseCase
import com.example.moviematch.domain.usecases.LoginUseCase
import com.example.moviematch.domain.usecases.LogoutUseCase
import com.example.moviematch.domain.usecases.RegisterUseCase
import com.example.moviematch.domain.usecases.RejectRequestUseCase
import com.example.moviematch.domain.usecases.RemoveFavUseCase
import com.example.moviematch.domain.usecases.SearchByEmailUseCase
import com.example.moviematch.domain.usecases.SendRequestUseCase
import com.example.moviematch.presentation.UI.screens.main.ProfileScreen
import com.example.moviematch.presentation.ViewModel.AuthViewModel
import com.example.moviematch.presentation.ViewModel.FavouritesViewModel
import com.example.moviematch.presentation.ViewModel.FilmsViewModel
import com.example.moviematch.presentation.ViewModel.FriendsViewModel
import com.example.moviematch.presentation.ViewModel.ProfileViewModel
import com.example.moviematch.presentation.ViewModel.SessionViewModel
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
        val favouriteFilmsRepository = FavouriteFilmsRepositoryImpl()
        val favouritesViewModel = FavouritesViewModel(
            getFavsUseCase = GetFavsUseCase(favouriteFilmsRepository),
            removeFavUseCase = RemoveFavUseCase(favouriteFilmsRepository),
            getCurrentIdUseCase = GetCurrentIdUseCase(authRepository)
        )
        val filmsViewModel = FilmsViewModel(getFilmsUseCase = GetFilmsUseCase(filmsRepository),
            addFavUseCase = AddFavUseCase(favouriteFilmsRepository),
            getCurrentIdUseCase = GetCurrentIdUseCase(authRepository))

        val friendsRepository = FriendsRepositoryImpl()
        val friendsViewModel = FriendsViewModel(
            GetAllFriendsUseCase(friendsRepository),
            GetAllRequestsUseCase(friendsRepository),
            AcceptRequestUseCase(friendsRepository),
            RejectRequestUseCase(friendsRepository),
            SendRequestUseCase(friendsRepository),
            GetCurrentIdUseCase(authRepository),
            SearchByEmailUseCase(friendsRepository),
            GetUserByIdUseCase(friendsRepository)
        )
        val profileViewModel = ProfileViewModel(
            GetCurrentEmailUseCase(authRepository),
            LogoutUseCase(authRepository)
            )
        val sessionRepository = SessionRepositoryImpl()
        val sessionViewModel = SessionViewModel(
            GetOrCreateSessionUseCase(sessionRepository),
            FinishSessionUseCase(sessionRepository),
            LikeFilmSessionUseCase(sessionRepository),
            GetCurrentIdUseCase(authRepository),
        )
        setContent {
            AppNavGraph(
                authViewModel = authViewModel,
                filmsViewModel = filmsViewModel,
                favouritesViewModel = favouritesViewModel,
                friendsViewModel = friendsViewModel,
                sessionViewModel = sessionViewModel,
                profileViewModel = profileViewModel
            )
        }
    }
}