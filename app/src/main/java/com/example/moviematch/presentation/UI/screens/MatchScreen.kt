package com.example.moviematch.presentation.UI.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.moviematch.domain.model.Film
import com.example.moviematch.presentation.UI.screens.main.FilmCard


@Composable
fun MatchScreen(
    matchFilm: Film?,
    onContinueClick: () -> Unit,
    onFinishClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
    Spacer(modifier = Modifier.weight(0.3f))
        Box(
            modifier = Modifier
                .width(350.dp)
                .height(650.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color(0xFFE5EDFA)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("У вас match!")
                Spacer(modifier = Modifier.height(10.dp))

                if (matchFilm == null) {
                    Text("Фильм не найден")
                } else {
                    FilmCard(matchFilm)
                    Spacer(modifier = Modifier.height(10.dp))

                    Row {
                        Button(onClick = onContinueClick) {
                            Text("Продолжить")
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Button(onClick = onFinishClick) {
                            Text("Закончить")
                        }
                    }
                }
            }
        }
    }
}