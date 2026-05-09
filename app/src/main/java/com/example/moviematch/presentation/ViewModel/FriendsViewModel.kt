package com.example.moviematch.presentation.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviematch.domain.model.User
import com.example.moviematch.domain.usecases.AcceptRequestUseCase
import com.example.moviematch.domain.usecases.GetAllFriendsUseCase
import com.example.moviematch.domain.usecases.GetAllRequestsUseCase
import com.example.moviematch.domain.usecases.GetCurrentIdUseCase
import com.example.moviematch.domain.usecases.RejectRequestUseCase
import com.example.moviematch.domain.usecases.SearchByEmailUseCase
import com.example.moviematch.domain.usecases.SendRequestUseCase
import com.example.moviematch.presentation.States.FindState
import com.example.moviematch.presentation.States.FriendsState
import com.example.moviematch.presentation.States.RequestState
import kotlinx.coroutines.launch

class FriendsViewModel(
    private val getAllFriendsUseCase: GetAllFriendsUseCase,
    private val getAllRequestsUseCase: GetAllRequestsUseCase,
    private val acceptRequestUseCase: AcceptRequestUseCase,
    private val rejectRequestUseCase: RejectRequestUseCase,
    private val sendRequestUseCase: SendRequestUseCase,
    private val getCurrentIdUseCase: GetCurrentIdUseCase,
    private val searchByEmailUseCase: SearchByEmailUseCase
): ViewModel() {
    var friendsState by mutableStateOf(FriendsState())
    private set
    var requestState by mutableStateOf(RequestState())
        private set
    var findState by mutableStateOf(FindState())
        private set

    init{
        loadFriends()
        loadRequests()
    }
    fun loadFriends(){
        val userId = getCurrentIdUseCase()
        friendsState = friendsState.copy(isLoading = true)
        viewModelScope.launch {
            friendsState = try{
                if (userId != null) {
                    friendsState.copy(isLoading = false, friends = getAllFriendsUseCase(userId))
                } else{
                    friendsState.copy(isLoading = false, errorMessage = "Ошибка загрузки друзей")
                }
            } catch (e: Exception){
                friendsState.copy(isLoading = false, errorMessage = "Ошибка загрузки друзей")

            }
        }
    }

    fun loadRequests(){
        val userId = getCurrentIdUseCase()
        requestState = requestState.copy(isLoading = true)
        viewModelScope.launch {
            requestState = try{
                if (userId != null) {
                    requestState.copy(isLoading = false, requests = getAllRequestsUseCase(userId))
                } else{
                    requestState.copy(isLoading = false, errorMessage = "Ошибка загрузки заявки")
                }
            } catch (e: Exception){
                requestState.copy(isLoading = false, errorMessage = "Ошибка загрузки заявки")

            }
        }
    }

    fun acceptRequest(requestId: String){
        viewModelScope.launch {
            try {
                acceptRequestUseCase(requestId)
                loadRequests()
                loadFriends()
            } catch (e: Exception) {
                requestState = requestState.copy(errorMessage = "Не удалось принять заявку")
            }
        }
    }

    fun rejectRequest(requestId: String) {
        viewModelScope.launch {
            try {
                rejectRequestUseCase(requestId)
                loadRequests()
            } catch (e: Exception) {
                requestState = requestState.copy(errorMessage = "Не удалось отклонить заявку")
            }
        }
    }

    fun sendRequestToFoundUser(toUser: User){
        val fromUserId = getCurrentIdUseCase()
        viewModelScope.launch {
            try {
                if(fromUserId != null) {
                    if( fromUserId == toUser.userId){
                        requestState = requestState.copy(errorMessage = "Нельзя добавить себя")
                    }
                    else {
                        sendRequestUseCase(fromUserId, toUser.userId)
                        loadRequests()
                        findState = findState.copy(
                            errorMessage = "Заявка отправлена",
                            foundUser = null
                        )
                    }
                }
                else{
                    requestState = requestState.copy(errorMessage = "Не удалось отправить заявку")
                }
            } catch (e: Exception) {
                requestState = requestState.copy(errorMessage = "Не удалось отправить заявку")
            }
        }
    }

    fun onEmailChange(newEmail: String) {
        findState = findState.copy(
            searchEmail = newEmail,
            foundUser = null,
            errorMessage = null
        )
    }

    fun searchUser(email: String){
        viewModelScope.launch {
            findState = findState.copy(isLoading = true)
            try {
                val user = searchByEmailUseCase(email)
                findState = if(user != null) {
                    findState.copy(foundUser = user, isLoading = false)
                } else{
                    findState.copy(errorMessage = "Не удалось найти пользователя", isLoading = false)
                }
            } catch (e: Exception) {
                findState = findState.copy(errorMessage = "Не удалось найти пользователя", isLoading = false)
            }
        }
    }
}