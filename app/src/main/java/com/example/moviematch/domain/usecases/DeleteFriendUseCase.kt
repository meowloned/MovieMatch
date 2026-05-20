package com.example.moviematch.domain.usecases

import com.example.moviematch.domain.repository.FriendsRepository

class DeleteFriendUseCase(private val friendsRepository: FriendsRepository) {
    suspend operator fun invoke(currentUserId: String, friendId: String) {
        friendsRepository.deleteFriend(friendId, currentUserId)
    }
}