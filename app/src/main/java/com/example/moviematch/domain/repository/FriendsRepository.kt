package com.example.moviematch.domain.repository

import com.example.moviematch.domain.model.Friend
import com.example.moviematch.domain.model.Request
import com.example.moviematch.domain.model.User


interface FriendsRepository {
    suspend fun sendRequest(fromUserId: String, toUserId: String)
    suspend fun acceptRequest(requestId: String)
    suspend fun rejectRequest(requestId: String)
    suspend fun getRequests(toUserId:String): List<Request>
    suspend fun getFriends(userId: String): List<Friend>
    suspend fun searchByEmail(email:String): User?
    suspend fun getUserById(userId : String): User?
}