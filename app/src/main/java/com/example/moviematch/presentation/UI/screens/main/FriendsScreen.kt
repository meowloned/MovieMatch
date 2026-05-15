package com.example.moviematch.presentation.UI.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviematch.domain.model.Friend
import com.example.moviematch.domain.model.Request
import com.example.moviematch.presentation.States.FriendsState
import com.example.moviematch.presentation.States.RequestState
import com.example.moviematch.presentation.UI.components.BottomNavBar
import com.example.moviematch.presentation.ViewModel.FriendsViewModel

@Composable
fun FriendsScreen(
    friendsViewModel: FriendsViewModel,
    onProfileClick: () -> Unit,
    onFavClick: () -> Unit,
    onMainClick: () -> Unit,
    onFriendsClick: () -> Unit
) {
    val findState = friendsViewModel.findState
    val requestsState = friendsViewModel.requestState
    val friendsState = friendsViewModel.friendsState
    var friendsExpanded by remember { mutableStateOf(false) }
    var requestsExpanded by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        friendsViewModel.loadFriends()
        friendsViewModel.loadRequests()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFBBD0ED)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = findState.searchEmail,
                    onValueChange = { newEmail ->
                        friendsViewModel.onEmailChange(newEmail)
                    },
                    label = {
                        Text("Введите email")
                    },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = {
                        friendsViewModel.searchUser(findState.searchEmail)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Поиск"
                    )
                }
            }
            if (findState.isLoading) {
                Spacer(modifier = Modifier.weight(1f))
                CircularProgressIndicator(color = Color(0xFF2E3E6D))
            } else if (findState.foundUser == null) {
                Spacer(modifier = Modifier.height(20.dp))
                if (!findState.errorMessage.isNullOrEmpty()) {
                    Text(text = findState.errorMessage ?: "", color = Color(0xFF2E3E6D))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            requestsExpanded = !requestsExpanded
                        }
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Заявки в друзья",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start,
                            color = Color(0xFF2E3E6D)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = if (requestsExpanded) "▲" else "▼",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End,
                        color = Color(0xFF2E3E6D)
                    )
                }
                if (requestsExpanded) {
                    if (requestsState.isLoading){
                        Spacer(modifier = Modifier.weight(1f))
                        CircularProgressIndicator(color = Color(0xFF2E3E6D))
                    }
                    else{
                        if (!requestsState.requests.isEmpty()) {
                            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(
                                    requestsState.requests
                                ) { request ->
                                    RequestCard(
                                        requestsState,
                                        request,
                                        {friendsViewModel.acceptRequest(request.requestId)},
                                        {friendsViewModel.rejectRequest(request.requestId)}
                                    )
                                }
                            }
                        }
                        else if (!findState.errorMessage.isNullOrEmpty()) {
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(text = findState.errorMessage ?: "", color = Color(0xFF2E3E6D), textAlign = TextAlign.Center)
                        }
                        else {
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                "У вас ещё нет заявок",
                                color = Color(0xFF2E3E6D)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            friendsExpanded = !friendsExpanded
                        }
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Друзья",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        color = Color(0xFF2E3E6D)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = if (friendsExpanded) "▲" else "▼",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End,
                        color = Color(0xFF2E3E6D)
                    )
                }

                if (friendsExpanded) {
                    if (friendsState.isLoading){
                        Spacer(modifier = Modifier.weight(1f))
                        CircularProgressIndicator(color = Color(0xFF2E3E6D))
                    }
                    else{
                        if (!friendsState.friends.isEmpty()) {
                            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(
                                    friendsState.friends
                                ) { friend ->
                                    FriendCard(
                                        friend,
                                        friendsState
                                    )
                                }
                            }
                        } else {
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                "У вас ещё нет друзей",
                                color = Color(0xFF2E3E6D)
                            )
                        }
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(40.dp))
                val user = findState.foundUser
                Card(modifier = Modifier
                    .padding(15.dp)
                    ) {
                    Row(
                        modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .background(color = Color(0xFFE5EDFA)),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(user.email, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF2E3E6D))
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = {
                                friendsViewModel.sendRequestToFoundUser(user)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                tint = Color(0xFF2E3E6D),
                                contentDescription = "Отправить заявку"
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            BottomNavBar("friends", onFavClick, onMainClick, onProfileClick, onFriendsClick)
        }
    }
}


@Composable
fun RequestCard(
    requestsState: RequestState,
    request: Request,
    onAccept: () -> Unit,
    onReject: () -> Unit
){
    Card {
        Row(modifier = Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically){
            Spacer(modifier = Modifier.weight(1f))
            Text(
                requestsState.usersEmails[request.fromUserId] ?: request.fromUserId,
                color = Color(0xFF2E3E6D)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    onAccept()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    tint = Color(0xFF4C5840),
                    contentDescription = "Принять"
                )
            }
            Spacer(modifier = Modifier.weight(0.1f))
            IconButton(
                onClick = {
                    onReject()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    tint = Color(0xFF910D38),
                    contentDescription = "Отклонить"
                )
            }
        }
    }
}

@Composable
fun FriendCard(
    friend: Friend,
    friendsState: FriendsState
){
    Card(modifier = Modifier
        .fillMaxWidth()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFE5EDFA))
            .height(40.dp),
            verticalAlignment = Alignment.CenterVertically){
            Spacer(modifier = Modifier.weight(1f))
            Text(
                friendsState.usersEmails[friend.friendId] ?: friend.friendId,
                color = Color(0xFF2E3E6D)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
