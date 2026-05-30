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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
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
import com.example.moviematch.presentation.States.FriendsState
import com.example.moviematch.presentation.UI.components.BottomNavBar
import com.example.moviematch.presentation.UI.components.SwipeableFilmCard
import com.example.moviematch.presentation.ViewModel.FilmsViewModel
import com.example.moviematch.presentation.ViewModel.FriendsViewModel
import com.example.moviematch.presentation.ViewModel.SessionViewModel


@Composable
fun MainScreen(
    filmsViewModel: FilmsViewModel,
    friendsViewModel: FriendsViewModel,
    sessionViewModel: SessionViewModel,
    onProfileClick: () -> Unit,
    onFavClick: () -> Unit,
    onMainClick: () -> Unit,
    onFriendsClick: () -> Unit,
    onMatchFound: (String) -> Unit
) {
    val film = filmsViewModel.getCurFilm()
    val state = filmsViewModel.state
    var friendsExpanded by remember { mutableStateOf(false) }
    val friendsState = friendsViewModel.friendsState
    val selectedText =
        if (filmsViewModel.selectedId == null) {
            "только я"
        } else {
            friendsState.usersEmails[filmsViewModel.selectedId] ?: "друг"
        }
    val sessionState = sessionViewModel.state
    LaunchedEffect(sessionState.isMatched) {
        if (sessionState.isMatched) {
            sessionState.matchedFilmId?.let {
                onMatchFound(it)
            }
        }
    }
    when(state.isLoading){
        true->{
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color(0xFFBBD0ED)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column (modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    CircularProgressIndicator(color = Color(0xFF2E3E6D))
                    Spacer(modifier = Modifier.weight(1f))
                    BottomNavBar("main",
                        onFavClick,
                        onMainClick,
                        onProfileClick,
                        onFriendsClick,
                        modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                    )
                }
            }
        }

        false -> {
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(
                        color = Color(0xFFBBD0ED),
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                    Spacer(modifier = Modifier.height(75.dp))
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFE5EDFA)
                        )
                    ){
                        Row(
                            modifier = Modifier
                                .width(350.dp)
                                .clickable {
                                    friendsExpanded = !friendsExpanded
                                }
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "ищу с: $selectedText",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Start,
                                color = Color(0xFF2E3E6D)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = if (friendsExpanded) "▲" else "▼",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.End,
                                color = Color(0xFF2E3E6D)
                            )
                        }
                    }
                    if (friendsExpanded) {
                        Spacer(modifier = Modifier.weight(1f))
                            Column {
                                if (friendsState.isLoading) {
                                    CircularProgressIndicator(color = Color(0xFF2E3E6D))
                                } else {
                                    if (!friendsState.friends.isEmpty()) {
                                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                            item {
                                                Card(
                                                    modifier = Modifier
                                                        .clickable{ filmsViewModel.selectOnMe()
                                                            sessionViewModel.finishSession()}
                                                        .width(380.dp)

                                                ){
                                                    Row(modifier = Modifier
                                                        .fillMaxWidth()
                                                        .background(color = Color(0xFFE5EDFA))
                                                        .height(40.dp),
                                                        verticalAlignment = Alignment.CenterVertically){
                                                        Spacer(modifier = Modifier.weight(1f))
                                                        Text(
                                                            "только я",
                                                            color = Color(0xFF2E3E6D)
                                                        )
                                                        Spacer(modifier = Modifier.weight(1f))
                                                    }
                                                }
                                            }
                                            items(
                                                friendsState.friends
                                            ) { friend ->
                                                FriendCardSelect(
                                                    friend = friend,
                                                    friendsState = friendsState,
                                                    onClick = {
                                                        filmsViewModel.selectFriend(friend.friendId)
                                                        sessionViewModel.startSession(friend.friendId)
                                                        friendsExpanded = false
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFFBBD0ED)),
                            contentAlignment = Alignment.Center

                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Bottom,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(modifier = Modifier.weight(0.3f))
                                if (film == null) {
                                    Text("Фильмы закончились")
                                } else {
                                    key(film.id) {
                                        SwipeableFilmCard(
                                            film = film,
                                            onSwipedLeft = {
                                                filmsViewModel.nextFilm()
                                            },
                                            onSwipedRight = {
                                                if (filmsViewModel.selectedId == null) {
                                                    filmsViewModel.likeFilm()
                                                } else {
                                                    sessionViewModel.likeFilm(film)
                                                    filmsViewModel.nextFilm()
                                                }
                                            }
                                        )
                                    }
                                }
                        Spacer(modifier = Modifier.weight(0.8f))
                        BottomNavBar(
                            "main",
                            onFavClick,
                            onMainClick,
                            onProfileClick,
                            onFriendsClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .navigationBarsPadding()
                        )
                        }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun FriendCardSelect(
    onClick: () -> Unit,
    friend: Friend,
    friendsState: FriendsState
){
    Card(
        modifier = Modifier
        .clickable{onClick() }
        .width(380.dp)

    ){
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
