package com.example.moviematch.domain.model

data class Request (
    val requestId: String,
    val fromUserId: String,
    val toUserId: String,
    val status: String,
    val timestamp: Long
)