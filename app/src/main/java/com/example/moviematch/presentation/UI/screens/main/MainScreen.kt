package com.example.moviematch.presentation.UI.screens.main

import android.R
import android.inputmethodservice.Keyboard
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowLeft
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviematch.domain.model.Film
import com.example.moviematch.presentation.UI.components.getPosterResId
import com.example.moviematch.presentation.ViewModel.FilmsViewModel
import com.google.common.io.Files.append

@Composable
fun MainScreen(
    filmsViewModel: FilmsViewModel,
    onProfileClick: () -> Unit
) {
    val film = filmsViewModel.getCurFilm()
    val state = filmsViewModel.state
    when(state.isLoading){
        true->{
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color(0xFFCFCED1),
                    ),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        false -> {
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(
                        color = Color(0xFFCFCED1),
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                    Spacer(modifier = Modifier.weight(1.5f))

                    Box(
                        modifier = Modifier
                            .width(300.dp)
                            .height(650.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(
                                color = Color(0xFFACB7BB),
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
                                            containerColor = Color(0xFF36514E),
                                            contentColor = Color.White
                                        ),
                                        onClick = { filmsViewModel.dislikeFilm() }) {
                                        Text("Дизлайк")
                                    }
                                    Spacer(modifier = Modifier.width(105.dp))
                                    Button(
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFF36514E),
                                            contentColor = Color.White
                                        ),
                                        onClick = { filmsViewModel.likeFilm() }) {
                                        Text("Лайк")
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.weight(0.5f))
                    Box(modifier = Modifier.fillMaxWidth()
                        .height(55.dp)
                        .background(
                            color = Color(0xFFACB7BB),
                        )
                    ){
                        Row(modifier = Modifier.fillMaxSize()){
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = onProfileClick) {
                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = "Избранное"
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = onProfileClick) {
                                Icon(
                                    imageVector = Icons.Filled.Home,
                                    contentDescription = "Главный экран"
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = onProfileClick) {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = "Профиль"
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
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

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = film.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(200.dp)
        )

        Spacer(modifier = Modifier.height(3.dp))

        Column(
            modifier = Modifier
                .height(170.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = film.description_1,
                fontSize = 15.sp,
                modifier = Modifier.width(250.dp)
            )
            Spacer(modifier = Modifier.weight(0.1f))
            Row(modifier = Modifier.width(270.dp)){
                Text(text = "рейтинг: ", fontSize = 13.sp)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "$rating", fontSize = 13.sp)
            }

            Row(modifier = Modifier.width(270.dp)){
                Text(text = "длительность: ", fontSize = 13.sp)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "$duration", fontSize = 13.sp)
            }

            Row(modifier = Modifier.width(270.dp)){
                Text(text = "год: ", fontSize = 13.sp)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "$year", fontSize = 13.sp)
            }

            Row(modifier = Modifier.width(270.dp)){
                Text(text = "страна: ", fontSize = 13.sp)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "$country", fontSize = 13.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.width(200.dp))
            }

            Row(modifier = Modifier.width(270.dp)){
                Text(text = "жанр: ", fontSize = 13.sp)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "$genre", fontSize = 13.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.width(170.dp))
            }

            Text(
                text = "Узнать подробнее",
                fontSize = 12.sp,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                color = Color.DarkGray,
                modifier = Modifier.clickable {
                    showDetails = !showDetails
                }
            )

            if (showDetails) {
                Text(
                    text = film.description_2,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.width(250.dp)
                )

            }
        }
    }
}