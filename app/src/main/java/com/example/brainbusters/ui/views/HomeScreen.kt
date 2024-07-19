package com.example.brainbusters.ui.views

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.brainbusters.data.entities.Quiz
import com.example.brainbusters.data.entities.User
import com.example.brainbusters.ui.viewModels.QuizViewModel
import com.example.brainbusters.ui.viewModels.UsersActions
import com.example.brainbusters.ui.viewModels.UsersState


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreen(navController: NavController, userState: State<UsersState>, userAction: UsersActions, quizViewModel: QuizViewModel) {
    val geographyQuizzes by quizViewModel.getQuizzesByCategory("Geography").collectAsStateWithLifecycle(emptyList())
    val favoriteQuizzes by quizViewModel.getQuizzesByCategory("Favorite").collectAsStateWithLifecycle(emptyList())
    Log.d(TAG, "HomeScreen: " + quizViewModel.state.value.quizzes)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "", modifier = Modifier.padding(16.dp))

        Favorites(navController = navController, title = "Preferiti", quizzes = favoriteQuizzes)
        QuizCategory(navController = navController, title = "Geography", quizzes = geographyQuizzes)
        UserEliminateMe(userState, userAction)
    }
}

@Composable
fun Favorites(navController: NavController, title: String, quizzes: List<Quiz>) {
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
            items(quizzes) { quiz ->
                QuizItem(navController = navController, title = quiz.title)
            }
        }
    }
}

@Composable
fun QuizCategory(navController: NavController, title: String, quizzes: List<Quiz>) {
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
            items(quizzes) { quiz ->
                QuizItem(navController = navController, title = quiz.title)
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
fun UserEliminateMe(state: State<UsersState>, action: UsersActions) {
    LazyColumn(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        items(state.value.users) { user ->
            UserItem(item = user, onDelete = { action.removeUser(user.userId) })
        }
    }
}

@Composable
fun UserItem(item: User, onDelete: () -> Unit) {
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