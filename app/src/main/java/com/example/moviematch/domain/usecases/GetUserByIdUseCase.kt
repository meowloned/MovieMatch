package com.example.moviematch.domain.usecases

import com.example.moviematch.domain.model.User
import com.example.moviematch.domain.repository.FriendsRepository

class GetUserByIdUseCase(private val friendsRepository: FriendsRepository) {
    suspend operator fun invoke(userId: String): User?{
        return friendsRepository.getUserById(userId)
    }
}