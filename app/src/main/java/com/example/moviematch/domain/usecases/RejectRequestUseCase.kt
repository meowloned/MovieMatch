package com.example.moviematch.domain.usecases

import com.example.moviematch.domain.repository.FriendsRepository

class RejectRequestUseCase(private val friendsRepository: FriendsRepository){
    suspend operator fun invoke(requestId: String){
        friendsRepository.rejectRequest(requestId)
    }
}