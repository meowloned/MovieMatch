package com.example.moviematch.data.repositoryImpl

import com.example.moviematch.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl : AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    override fun getCurrentEmail(): String? {
        return auth.currentUser?.email
    }

    override fun getCurrentId(): String? {
        return auth.currentUser?.uid
    }

    override suspend fun login(email: String, password: String): Result<Unit> {
        return runCatching {
            auth.signInWithEmailAndPassword(email, password).await()
            Unit
        }
    }

    override suspend fun register(email: String, password: String): Result<Unit> {
        return runCatching {
            auth.createUserWithEmailAndPassword(email, password).await()
            Unit
        }
    }

    override fun logout(){
        auth.signOut()
    }
}