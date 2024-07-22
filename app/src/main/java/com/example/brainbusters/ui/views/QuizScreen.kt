package com.example.brainbusters.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.brainbusters.Notification
import com.example.brainbusters.Routes
import com.example.brainbusters.data.entities.QuizDone
import com.example.brainbusters.ui.viewModels.QuestionViewModel
import com.example.brainbusters.ui.viewModels.NotificationsViewModel
import com.example.brainbusters.ui.viewModels.QuizDoneViewModel
import kotlinx.coroutines.delay
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun QuizScreen(
    navController: NavController,
    quizId: Int,
    quizTitle: String,
    questionViewModel: QuestionViewModel,
    quizDoneViewModel: QuizDoneViewModel,
    notificationsViewModel: NotificationsViewModel,
    userId: Int
) {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var selectedAnswerIndex by remember { mutableStateOf<Int?>(null) }
    var isAnswered by remember { mutableStateOf(false) }

    // Retrieve questions from the database
    val questions by questionViewModel.getQuestionsByQuizId(quizId).collectAsStateWithLifecycle(emptyList())
    val currentQuestion = questions.getOrNull(currentQuestionIndex)

    // Retrieve responses for the current question
    val responses by questionViewModel.getResponsesByQuestionId(currentQuestion?.questionId ?: 0)
        .collectAsStateWithLifecycle(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = quizTitle) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LaunchedEffect(currentQuestionIndex, isAnswered) {
            if (isAnswered) {
                delay(1000)
                if (currentQuestionIndex < questions.size - 1) {
                    currentQuestionIndex++
                    isAnswered = false
                    selectedAnswerIndex = null
                } else {
                    val ciao = 10
                    val quizDone = QuizDone(
                        quizId = quizId,
                        userId = userId,
                        score = score
                    )
                    quizDoneViewModel.insertOrUpdate(quizDone)

                    // Create a notification
                    val timestampMillis = System.currentTimeMillis()
                    val instant = Instant.ofEpochMilli(timestampMillis)
                    val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy HH:mm:ss")
                        .withZone(ZoneId.systemDefault())
                    val timestamp = formatter.format(instant)
                    val notification = Notification(
                        id = quizId,
                        message = "Quiz completed $quizTitle with score: $score",
                        timestamp = timestamp
                    )
                    notificationsViewModel.addNotification(notification)

                    // Navigate to score screen
                    navController.navigate("scoreScreen/$score/$quizTitle")
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            currentQuestion?.let { question ->
                Text(text = question.question)
                Spacer(modifier = Modifier.height(16.dp))
                responses.forEachIndexed { index, response ->
                    Button(
                        onClick = {
                            selectedAnswerIndex = index
                            if (response.score == 5) {
                                score += 1
                            }
                            isAnswered = true
                        },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        colors = if (selectedAnswerIndex == index) {
                            ButtonDefaults.buttonColors(containerColor = if (response.score == 5) Color.Green else Color.Red)
                        } else {
                            ButtonDefaults.buttonColors()
                        }
                    ) {
                        Text(text = response.text)
                    }
                }
            }
        }
    }
}

@Composable
fun ScoreScreen(
    navController: NavController,
    score: Int,
    quizTitle: String,
    quizId: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Your score: $score", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                navController.navigate(Routes.quizScreen(quizId, quizTitle))
            }) {
                Text(text = "Restart Quiz")
            }
            Button(onClick = {
                val timestampMillis = System.currentTimeMillis()
                val timestampSeconds = timestampMillis / 1000
                val instant = Instant.ofEpochSecond(timestampSeconds)
                val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")
                    .withZone(ZoneId.of("UTC"))
                val dateString = formatter.format(instant)
                navController.navigate(Routes.homeScreen) {
                    popUpTo(Routes.homeScreen) { inclusive = true }
                }
            }) {
                Text(text = "Go to Home")
            }
        }
    }
}