package com.example.moviematch.domain.usecases

import com.example.moviematch.domain.model.Request
import com.example.moviematch.domain.repository.FriendsRepository

class GetAllRequestsUseCase(private val friendsRepository: FriendsRepository) {
    suspend operator fun invoke(userId:String): List<Request>{
        return friendsRepository.getRequests(userId)
    }
}