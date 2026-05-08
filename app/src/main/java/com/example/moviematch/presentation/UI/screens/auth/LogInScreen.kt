package com.example.moviematch.presentation.UI.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.moviematch.presentation.ViewModel.AuthViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onRegisterClick: () -> Unit
) {
    val state = authViewModel.state
    when(state.isLoading){
        true -> {
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
            Column(modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color(0xFFCFCED1),
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = state.email,
                    onValueChange = {newEmail -> authViewModel.onEmailChange(newEmail)},
                    label = { Text("Введите email") },
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(modifier = Modifier.height(15.dp))
                OutlinedTextField(
                    value = state.password,
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = {newPassword -> authViewModel.onPasswordChange(newPassword)},
                    label = { Text("Введите пароль") },
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {authViewModel.login() }
                ) {
                    Text("Вход")
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text("У меня ещё нет аккаунта")
                Text("Регистрация", modifier = Modifier.clickable{onRegisterClick()})
                if (!state.errorMessage.isNullOrEmpty()) {
                    Text(text = state.errorMessage ?: "")
                }
            }
        }
    }
}
