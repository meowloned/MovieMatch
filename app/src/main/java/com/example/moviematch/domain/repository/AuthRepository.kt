package com.example.moviematch.domain.repository

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun register(email: String, password: String): Result<Unit>
    fun logout()
    fun getCurrentId(): String?
    fun getCurrentEmail():String?
}