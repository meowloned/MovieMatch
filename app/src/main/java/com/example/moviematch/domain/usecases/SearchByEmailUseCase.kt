package com.example.moviematch.domain.usecases

import com.example.moviematch.domain.model.User
import com.example.moviematch.domain.repository.FriendsRepository

class SearchByEmailUseCase(private val friendsRepository: FriendsRepository) {
    suspend  operator fun invoke(email: String): User?{
        return friendsRepository.searchByEmail(email)
    }
}