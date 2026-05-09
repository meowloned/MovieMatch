package com.example.moviematch.domain.usecases

import com.example.moviematch.domain.repository.FriendsRepository

class SendRequestUseCase(private val friendsRepository: FriendsRepository) {
    suspend operator fun invoke(fromUserId:String, toUserId: String){
        friendsRepository.sendRequest(fromUserId, toUserId)
    }
}