package com.example.brainbusters.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.brainbusters.data.entities.User
import com.example.brainbusters.ui.viewModels.UsersActions
import com.example.brainbusters.ui.viewModels.UsersState

@Composable
fun HomeScreen(navController: NavController, state: State<UsersState>, action: UsersActions) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "", modifier = Modifier.padding(16.dp))

        Favorites(navController = navController, title = "Preferiti")
        QuizCategory(navController = navController, title = "Geography")
        UserEliminateMe(state, action)
    }
}

@Composable
fun Favorites(navController: NavController, title: String) {
    Column(
        modifier = Modifier
            .padding(horizontal = 6.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .background(color = Color.hsv(60f, 1f, 0.9f), RoundedCornerShape(8.dp))
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(count = 10) { index ->
                QuizItem(navController = navController, title = "Quiz $index")
            }
        }
    }
}

@Composable
fun QuizCategory(navController: NavController, title: String) {
    Column(
        modifier = Modifier
            .padding(horizontal = 6.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(count = 10) { index ->
                QuizItem(navController = navController, title = "Quiz $index")
            }
        }
    }
}

@Composable
fun QuizItem(navController: NavController, title: String) {
    Card(
        modifier = Modifier
            .size(120.dp)
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate("quizScreen/$title")
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title)
        }
    }
}

@Composable
fun UserEliminateMe (state: State<UsersState>, action: UsersActions) {
    LazyColumn (
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        items(state.value.users) {
            UserItem(item = it, onDelete = { action.removeUser(it.userId )})
        }
    }
}

@Composable
fun UserItem(
    item: User,
    onDelete: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = item.userEmail)
        IconButton(onClick = onDelete) {
            Icon(Icons.Outlined.Close, "Remove User")
        }
    }
}