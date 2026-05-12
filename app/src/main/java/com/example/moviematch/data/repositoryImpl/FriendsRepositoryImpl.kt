package com.example.moviematch.data.repositoryImpl

import com.example.moviematch.domain.model.Friend
import com.example.moviematch.domain.model.Request
import com.example.moviematch.domain.model.User
import com.example.moviematch.domain.repository.FriendsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FriendsRepositoryImpl: FriendsRepository {
    val firestore = FirebaseFirestore.getInstance()
    override suspend fun acceptRequest(requestId: String) {
        val request =  firestore
            .collection("friendRequests")
            .document(requestId)
            .get().await()
        val fromUserId = request.getString("fromUserId")
        val toUserId = request.getString("toUserId")
        val timestamp = System.currentTimeMillis()
        if (fromUserId != null && toUserId != null) {
            val fromUser = Friend(
                fromUserId,
                timestamp
            )
            val toUser = Friend(
                toUserId,
                timestamp
            )
            firestore
                .collection("users")
                .document(fromUserId)
                .collection("friends")
                .document(toUserId)
                .set(toUser).await()
            firestore
                .collection("users")
                .document(toUserId)
                .collection("friends")
                .document(fromUserId)
                .set(fromUser).await()
        }
        else{
            throw Exception("Некорректная заявка")
        }
        firestore
            .collection("friendRequests")
            .document(requestId)
            .update("status", "accepted")
            .await()

    }

    override suspend fun getFriends(userId: String): List<Friend> {
        val snapshot = firestore
            .collection("users")
            .document(userId)
            .collection("friends")
            .get().await()
        val list = snapshot.documents.mapNotNull { document ->
            val friendId = document.getString("friendId") ?: ""
            val timestamp = document.getLong("timestamp") ?: 0L
            Friend(
                friendId,
                timestamp
            )
        }
        return list
    }

    override suspend fun getRequests(toUserId: String): List<Request> {
        val snapshot = firestore
            .collection("friendRequests")
            .whereEqualTo("toUserId", toUserId)
            .whereEqualTo("status", "pending")
            .get()
            .await()
       val list = snapshot.documents.mapNotNull { document ->
            val requestId = document.getString("requestId") ?: ""
            val fromUserId = document.getString("fromUserId") ?: ""
            val toUserId = document.getString("toUserId") ?: ""
           val status = document.getString("status") ?: ""
            val timestamp = document.getLong("timestamp") ?: 0L
            Request(
                requestId,
                fromUserId,
                toUserId,
                status,
                timestamp
           )
       }
        return list
    }

    override suspend fun rejectRequest(requestId: String) {
        firestore
            .collection("friendRequests")
            .document(requestId)
            .update("status", "rejected")
            .await()
    }

    override suspend fun sendRequest(fromUserId: String, toUserId: String) {
        val request = firestore.collection("friendRequests").document()
        val timestamp = System.currentTimeMillis()
        request.set(
            Request(
            request.id,
            fromUserId,
            toUserId,
            "pending",
            timestamp
        )).await()
    }

    override suspend fun searchByEmail(email:String): User? {
        val snapshot = firestore.collection("users")
            .whereEqualTo("email", email)
            .get().await()
        val user = snapshot.documents.firstOrNull()
        if (user != null) {
            val userId = user.getString("userId") ?: ""
            val email = user.getString("email") ?: ""
            val timestamp = user.getLong("timestamp") ?: 0L
            return User(
                userId,
                email,
                timestamp
            )
        }
        else{
            return null
        }
    }

    override suspend fun getUserById(userId : String): User?{
        val document = firestore.collection("users")
            .document(userId)
            .get()
            .await()

        if (!document.exists()) {
            return null
        }

        val email = document.getString("email") ?: ""
        val timestamp = document.getLong("timestamp") ?: 0L

        return User(
            userId = userId,
            email = email,
            timestamp = timestamp
        )
    }
}