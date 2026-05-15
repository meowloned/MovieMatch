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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.moviematch.presentation.States.AuthState
import com.example.moviematch.presentation.ViewModel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel,
    onLoginClick: () -> Unit,
) {
    val state = authViewModel.state
    when(state.isLoading){
        true -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color(0xFFBBD0ED),
                    ),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF2E3E6D))
            }
        }
        false -> {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color(0xFFBBD0ED),
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))
                OutlinedTextField(
                    value = state.email,
                    onValueChange = {newEmail -> authViewModel.onEmailChange(newEmail)},
                    label = { Text("Введите email", color = Color(0xFF2E3E6D)) },
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(modifier = Modifier.height(15.dp))
                OutlinedTextField(
                    value = state.password,
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = {newPassword -> authViewModel.onPasswordChange(newPassword)},
                    label = { Text("Введите пароль", color = Color(0xFF2E3E6D)) },
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7087BB),
                        contentColor = Color.White),
                    onClick = {authViewModel.register()
                        authViewModel.onEmailChange("")
                        authViewModel.onPasswordChange("")
                    }
                ) {
                    Text("Зарегистрироваться")
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text("У меня уже есть аккаунт",
                    color = Color(0xFF2E3E6D))
                Text("Вход",
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    color = Color(0xFF2E3E6D),
                    modifier = Modifier.clickable{onLoginClick()})
                Spacer(modifier = Modifier.height(20.dp))
                if (!state.errorMessage.isNullOrEmpty()) {
                    Text(text = state.errorMessage ?: "", color = Color(0xFF2E3E6D), textAlign = TextAlign.Center)
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}
