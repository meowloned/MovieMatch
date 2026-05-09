package com.example.moviematch.domain.usecases

import com.example.moviematch.domain.repository.FriendsRepository

class AcceptRequestUseCase(private val friendsRepository: FriendsRepository) {
    suspend operator fun invoke(requestId: String){
        friendsRepository.acceptRequest(requestId)
    }
}