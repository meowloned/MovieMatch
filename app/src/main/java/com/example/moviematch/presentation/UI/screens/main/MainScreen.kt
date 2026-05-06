package com.example.moviematch.presentation.UI.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviematch.domain.model.Film
import com.example.moviematch.presentation.UI.components.getPosterResId
import com.example.moviematch.presentation.ViewModel.FilmsViewModel

@Composable
fun MainScreen(filmsViewModel: FilmsViewModel) {
    val film = filmsViewModel.getCurFilm()
    val state = filmsViewModel.state
    when(state.isLoading){
        true->{
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        false -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .height(650.dp)
                        .background(color = Color(0xFFFFFBD4), shape = RoundedCornerShape(30.dp)),
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
                                Button(onClick = { filmsViewModel.dislikeFilm() }) {
                                    Text("Дизлайк")
                                }
                                Spacer(modifier = Modifier.width(110.dp))
                                Button(onClick = { filmsViewModel.likeFilm() }) {
                                    Text("Лайк")
                                }
                            }
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

    Column(
        modifier = Modifier.width(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = film.title,
            modifier = Modifier.clip(RoundedCornerShape(20.dp))
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = film.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .height(170.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = film.description_1,
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = "Узнать подробнее",
                fontSize = 15.sp,
                modifier = Modifier.clickable {
                    showDetails = !showDetails
                }
            )

            Spacer(modifier = Modifier.height(7.dp))

            if (showDetails) {
                Text(
                    text = film.description_2,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(7.dp))
            }

            Text(text = film.year.toString(), fontSize = 15.sp)

            Spacer(modifier = Modifier.height(7.dp))

            Text(text = film.rating.toString(), fontSize = 15.sp)

            Spacer(modifier = Modifier.height(7.dp))

            Text(text = film.duration, fontSize = 15.sp)

            Spacer(modifier = Modifier.height(7.dp))

            Text(text = film.country, fontSize = 15.sp, textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(7.dp))

            Text(text = film.genre, fontSize = 15.sp, textAlign = TextAlign.Center)
        }
    }
}