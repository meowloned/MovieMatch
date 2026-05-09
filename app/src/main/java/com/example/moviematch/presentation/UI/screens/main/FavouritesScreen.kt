package com.example.moviematch.presentation.UI.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviematch.domain.model.Film
import com.example.moviematch.presentation.UI.components.BottomNavBar
import com.example.moviematch.presentation.UI.components.getPosterResId
import com.example.moviematch.presentation.ViewModel.FavouritesViewModel
import com.example.moviematch.presentation.ViewModel.FilmsViewModel

@Composable
fun FavouritesScreen(
    favouritesViewModel: FavouritesViewModel,
    filmsViewModel: FilmsViewModel,
    onProfileClick: () -> Unit,
    onFavClick: () -> Unit,
    onMainClick: () -> Unit,
    onFriendsClick: () -> Unit
){
    LaunchedEffect(Unit) {
        favouritesViewModel.loadFavs()
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            color = Color(0xFFBBD0ED)
        ),
        contentAlignment = Alignment.Center
    ){
        val state = favouritesViewModel.state
        when(state.isLoading){
            true ->{
                Column (modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    CircularProgressIndicator(color = Color(0xFF2E3E6D))
                    Text("Минуту, мы загружаем ваше избранное",
                        textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.weight(1f))
                    BottomNavBar("fav", onFavClick, onMainClick, onProfileClick, onFriendsClick)
                }
            }
            false -> {

                Column (modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    if (state.favs.isEmpty()) {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            "У вас пока ещё нет понравившихся фильмов",
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    else {
                        Spacer(modifier = Modifier.height(30.dp))
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.weight(1f)
                                .padding(start = 10.dp, end = 10.dp),
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            verticalArrangement = Arrangement.spacedBy(15.dp)
                        ) {
                            val favoriteFilms = state.favs
                            items(favoriteFilms) { favfilm ->
                                FavoriteFilmCard(
                                    favFilm = filmsViewModel.searchById(favfilm.filmId),
                                    onRemove = {
                                        favouritesViewModel.removeFav(favfilm.filmId)
                                    }
                                )
                            }
                        }

                    }
                    BottomNavBar("fav", onFavClick, onMainClick, onProfileClick, onFriendsClick)
                }
            }
        }

    }

}

@Composable
fun FavoriteFilmCard(favFilm: Film?, onRemove:()->Unit) {
    Box(modifier = Modifier
        .height(295.dp)
        .background(
            color = Color(0xFFE5EDFA),
            shape = RoundedCornerShape(15.dp)
        ),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (favFilm!= null) {
                val imageId = getPosterResId(favFilm.posterName)
                Box {
                    Image(
                        painter = painterResource(id = imageId),
                        contentDescription = favFilm.title,
                        modifier = Modifier
                            .padding(7.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                    IconButton(
                        onClick = {onRemove()},
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Удалить из избранного",
                            tint = Color(0xFF910D38)
                        )
                    }
                }
                Text(text = favFilm.title,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF2E3E6D),
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(7.dp))
            }
            else{
                Text(text = "Ошибка загрузки фильма",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF2E3E6D),
                    fontSize = 15.sp
                )
            }
        }
    }
}

