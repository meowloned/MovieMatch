package com.example.moviematch.domain.usecases

import com.example.moviematch.domain.model.Friend
import com.example.moviematch.domain.repository.FriendsRepository

class GetAllFriendsUseCase(private val friendsRepository: FriendsRepository) {
    suspend operator fun invoke(userId: String): List<Friend>{
        return friendsRepository.getFriends(userId)
    }
}