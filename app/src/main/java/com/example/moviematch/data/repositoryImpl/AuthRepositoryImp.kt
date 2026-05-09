package com.example.moviematch.data.repositoryImpl

import com.example.moviematch.domain.model.User
import com.example.moviematch.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl : AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
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
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid
            val users = firestore.collection("users")
            val timestamp = System.currentTimeMillis()
            if (userId != null) {
                val user = User(
                    userId,
                    email,
                    timestamp
                )
                users.document(user.userId).set(user).await()
            }
            else{
                throw Exception("Не удалось получить userId")
            }
            Unit
        }
    }

    override fun logout(){
        auth.signOut()
    }
}