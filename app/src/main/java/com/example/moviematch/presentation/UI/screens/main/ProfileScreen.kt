package com.example.moviematch.presentation.UI.screens.main

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviematch.presentation.UI.components.BottomNavBar
import com.example.moviematch.presentation.ViewModel.AuthViewModel
import com.example.moviematch.presentation.ViewModel.ProfileViewModel


@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    onProfileClick: () -> Unit,
    onFavClick: () -> Unit,
    onMainClick: () -> Unit,
    onFriendsClick: () -> Unit,
    onLogClick: () -> Unit
) {
    val state = profileViewModel.state
    val email = state.email
    LaunchedEffect(Unit) {
        profileViewModel.loadProfile()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFBBD0ED)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Card(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .background(color = Color(0xFFE5EDFA)),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    if (!email.isNullOrEmpty()) {
                        Text(
                            email,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color(0xFF2E3E6D)
                        )
                    } else if (!state.errorMessage.isNullOrEmpty()) {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(text = state.errorMessage ?: "", color = Color(0xFF2E3E6D))
                    } else {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            "вы не вошли в аккаунт",
                            color = Color(0xFF2E3E6D)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            profileViewModel.logout()
                            onLogClick()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            tint = Color(0xFF2E3E6D),
                            contentDescription = "Выйти из аккаунта"
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            BottomNavBar("profile", onFavClick, onMainClick, onProfileClick, onFriendsClick)
        }
    }
}
