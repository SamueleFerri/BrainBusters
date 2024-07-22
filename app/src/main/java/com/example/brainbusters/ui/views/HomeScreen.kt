package com.example.brainbusters.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
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
import com.example.brainbusters.Routes
import com.example.brainbusters.data.entities.Quiz
import com.example.brainbusters.ui.viewModels.QuizViewModel
import com.example.brainbusters.ui.viewModels.UsersActions
import com.example.brainbusters.ui.viewModels.UsersState

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreen(navController: NavController, userState: State<UsersState>, userAction: UsersActions, quizViewModel: QuizViewModel) {
    val geographyQuizzes by quizViewModel.getQuizzesByCategory("Geography").collectAsStateWithLifecycle(emptyList())
    val favoriteQuizzes by quizViewModel.getFavoriteQuizzes().collectAsStateWithLifecycle(emptyList())

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "", modifier = Modifier.padding(16.dp))

        QuizCategory(navController = navController, title = "Geography", quizzes = geographyQuizzes, quizViewModel = quizViewModel)
        Favorites(navController = navController, title = "Favorites", quizzes = favoriteQuizzes, quizViewModel = quizViewModel)
    }
}

@Composable
fun Favorites(navController: NavController, title: String, quizzes: List<Quiz>, quizViewModel: QuizViewModel) {
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
                QuizItem(navController = navController, quiz = quiz, quizViewModel = quizViewModel)
            }
        }
    }
}

@Composable
fun QuizCategory(navController: NavController, title: String, quizzes: List<Quiz>, quizViewModel: QuizViewModel) {
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
                QuizItem(navController = navController, quiz = quiz, quizViewModel = quizViewModel)
            }
        }
    }
}

@Composable
fun QuizItem(navController: NavController, quiz: Quiz, quizViewModel: QuizViewModel) {
    Card(
        modifier = Modifier
            .size(120.dp)
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate(Routes.quizScreen(quiz.quizId, quiz.title))
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    val updatedQuiz = quiz.copy(isFavorite = !quiz.isFavorite)
                    quizViewModel.updateQuiz(updatedQuiz)
                }) {
                    Icon(
                        imageVector = if (quiz.isFavorite) Icons.Filled.Star else Icons.Outlined.StarBorder,
                        contentDescription = if (quiz.isFavorite) "Remove from favorites" else "Add to favorites"
                    )
                }
                Text(text = quiz.title)
            }
        }
    }
}