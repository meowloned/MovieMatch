package com.example.moviematch.presentation.UI.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

@Composable
fun FilmCardMatch(
    film: Film,
    onFinishClick:() -> Unit,
    onContinueClick:() -> Unit
) {
    val imageId = getPosterResId(film.posterName)
    val year = film.year
    val rating = film.rating
    val country = film.country
    val genre = film.genre
    val duration = film.duration

    Column(
        modifier = Modifier.width(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
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
            modifier = Modifier.width(240.dp)
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = film.description_1,
            fontSize = 15.sp,
            color = Color(0xFF2E3E6D),
            modifier = Modifier.width(270.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
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
        Spacer(modifier = Modifier.height(10.dp))
        Spacer(modifier = Modifier.height(10.dp))

        Row {
            Button(onClick = onContinueClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7087BB),
                    contentColor = Color.White
                )) {
                Text("Продолжить")
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(onClick = onFinishClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7087BB),
                    contentColor = Color.White
                )) {
                Text("Закончить")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}
