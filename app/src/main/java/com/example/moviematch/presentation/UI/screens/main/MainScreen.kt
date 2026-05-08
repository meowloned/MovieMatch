package com.example.moviematch.presentation.UI.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviematch.domain.model.Film
import com.example.moviematch.presentation.UI.components.BottomNavBar
import com.example.moviematch.presentation.UI.components.getPosterResId
import com.example.moviematch.presentation.ViewModel.FilmsViewModel

@Composable
fun MainScreen(
    filmsViewModel: FilmsViewModel,
    onProfileClick: () -> Unit,
    onFavClick: () -> Unit,
    onMainClick: () -> Unit
) {
    val film = filmsViewModel.getCurFilm()
    val state = filmsViewModel.state
    when(state.isLoading){
        true->{
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color(0xFFBBD0ED)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column (modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    CircularProgressIndicator(color = Color(0xFF2E3E6D))
                    Spacer(modifier = Modifier.weight(1f))
                    BottomNavBar("main", onFavClick, onMainClick, onProfileClick)
                }
            }
        }

        false -> {
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(
                        color = Color(0xFFBBD0ED),
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                    Spacer(modifier = Modifier.weight(1.2f))

                    Box(
                        modifier = Modifier
                            .width(350.dp)
                            .height(650.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(
                                color = Color(0xFFE5EDFA),
                                shape = RoundedCornerShape(30.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (film == null) {
                            Text("Фильмы закончились")
                        } else {
                            Column() {
                                Spacer(modifier = Modifier.height(10.dp))
                                FilmCard(film)
                                Spacer(modifier = Modifier.height(10.dp))
                                Row() {
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Button(
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFF7087BB),
                                            contentColor = Color.White
                                        ),
                                        onClick = { filmsViewModel.dislikeFilm() }) {
                                        Text("Дизлайк")
                                    }
                                    Spacer(modifier = Modifier.width(105.dp))
                                    Button(
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFF7087BB),
                                            contentColor = Color.White
                                        ),
                                        onClick = { filmsViewModel.likeFilm() }) {
                                        Text("Лайк")
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.weight(0.8f))
                    BottomNavBar("main", onFavClick, onMainClick, onProfileClick)
                }
            }
        }
    }
}
@Composable
fun FilmCard(film: Film) {
    var showDetails by remember { mutableStateOf(false) }
    val imageId = getPosterResId(film.posterName)
    val scrollState = rememberScrollState()
    val year = film.year
    val rating = film.rating
    val country = film.country
    val genre = film.genre
    val duration = film.duration

    Column(
        modifier = Modifier.width(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = film.title,
            modifier = Modifier.clip(RoundedCornerShape(20.dp))
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = film.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color(0xFF2E3E6D),
            modifier = Modifier.width(200.dp)
        )

        Spacer(modifier = Modifier.height(5.dp))

        Column(
            modifier = Modifier
                .height(170.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = film.description_1,
                fontSize = 15.sp,
                color = Color(0xFF2E3E6D),
                modifier = Modifier.width(250.dp)
            )
            Spacer(modifier = Modifier.weight(0.05f))
            Row(modifier = Modifier.width(270.dp)){
                Text(text = "рейтинг: ",
                    fontSize = 13.sp,
                    color = Color(0xFF2E3E6D))
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "$rating",
                    color = Color(0xFF2E3E6D),
                    fontSize = 13.sp)
            }

            Row(modifier = Modifier.width(270.dp)){
                Text(text = "длительность: ",
                    color = Color(0xFF2E3E6D),
                    fontSize = 13.sp)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "$duration",
                    color = Color(0xFF2E3E6D),
                    fontSize = 13.sp)
            }

            Row(modifier = Modifier.width(270.dp)){
                Text(text = "год: ",
                    color = Color(0xFF2E3E6D),
                    fontSize = 13.sp)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "$year",
                    color = Color(0xFF2E3E6D),
                    fontSize = 13.sp)
            }

            Row(modifier = Modifier.width(270.dp)){
                Text(text = "страна: ",
                    color = Color(0xFF2E3E6D),
                    fontSize = 13.sp)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "$country", fontSize = 13.sp,
                    textAlign = TextAlign.End,
                    color = Color(0xFF2E3E6D),
                    modifier = Modifier.width(200.dp))
            }

            Row(modifier = Modifier.width(270.dp)){
                Text(text = "жанр: ",
                    color = Color(0xFF2E3E6D),
                    fontSize = 13.sp)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "$genre", fontSize = 13.sp,
                    textAlign = TextAlign.End,
                    color = Color(0xFF2E3E6D),
                    modifier = Modifier.width(170.dp))
            }

            Text(
                text = "Узнать подробнее",
                fontSize = 12.sp,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                color = Color(0xFF495589),
                modifier = Modifier.clickable {
                    showDetails = !showDetails
                }
            )

            if (showDetails) {
                Text(
                    text = film.description_2,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Justify,
                    color = Color(0xFF2E3E6D),
                    modifier = Modifier.width(250.dp)
                )

            }
        }
    }
}