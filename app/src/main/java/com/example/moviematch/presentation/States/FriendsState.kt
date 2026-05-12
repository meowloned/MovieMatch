package com.example.moviematch.presentation.States

import com.example.moviematch.domain.model.Friend

data class FriendsState(
    val friends: List<Friend> = emptyList(),
    val isLoading: Boolean = false,
    val usersEmails: Map<String, String> = emptyMap(),
    val errorMessage: String? = null
)