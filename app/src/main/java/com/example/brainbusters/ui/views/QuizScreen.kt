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
    import androidx.navigation.NavController
    import com.example.brainbusters.Notification
    import com.example.brainbusters.Routes
    import com.example.brainbusters.ui.viewModels.ViewModelNotifications
    import kotlinx.coroutines.delay

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun QuizScreen(navController: NavController, quizTitle: String) {
        var currentQuestionIndex by remember { mutableStateOf(0) }
        var score by remember { mutableStateOf(0) }
        var selectedAnswerIndex by remember { mutableStateOf<Int?>(null) }
        var isAnswered by remember { mutableStateOf(false) }

        val questions = listOf(
            "Question 1",
            "Question 2",
            "Question 3",
            "Question 4",
            "Question 5",
            "Question 6",
            "Question 7",
            "Question 8",
            "Question 9",
            "Question 10"
        )

        val options = listOf(
            listOf("Option 1", "Option 2", "Option 3", "Option 4"), // Index 0 is correct
            listOf("Option 1", "Option 2", "Option 3", "Option 4"), // Index 1 is correct
            listOf("Option 1", "Option 2", "Option 3", "Option 4"), // Index 2 is correct
            listOf("Option 1", "Option 2", "Option 3", "Option 4"), // Index 3 is correct
            listOf("Option 1", "Option 2", "Option 3", "Option 4"), // Index 0 is correct
            listOf("Option 1", "Option 2", "Option 3", "Option 4"), // Index 1 is correct
            listOf("Option 1", "Option 2", "Option 3", "Option 4"), // Index 2 is correct
            listOf("Option 1", "Option 2", "Option 3", "Option 4"), // Index 3 is correct
            listOf("Option 1", "Option 2", "Option 3", "Option 4"), // Index 0 is correct
            listOf("Option 1", "Option 2", "Option 3", "Option 4")  // Index 1 is correct
        )

        val correctAnswers = listOf(0, 1, 2, 3, 0, 1, 2, 3, 0, 1)

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
                        navController.navigate(Routes.scoreScreen(score))
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                if (currentQuestionIndex < questions.size) {
                    Column {
                        Text(
                            text = questions[currentQuestionIndex],
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        options[currentQuestionIndex].forEachIndexed { index, option ->
                            Button(
                                onClick = {
                                    if (!isAnswered) {
                                        selectedAnswerIndex = index
                                        isAnswered = true
                                        if (index == correctAnswers[currentQuestionIndex]) {
                                            score++
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = when {
                                        isAnswered && index == correctAnswers[currentQuestionIndex] -> Color.Green
                                        isAnswered && index == selectedAnswerIndex -> Color.Red
                                        else -> MaterialTheme.colorScheme.primary
                                    }
                                )
                            ) {
                                Text(text = option)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ScoreScreen(navController: NavController, score: Int, viewModel: ViewModelNotifications, quizTitle: String) {
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
                    navController.navigate("quiz_screen/restart") {
                        popUpTo("quiz_screen/restart") { inclusive = true }
                    }
                }) {
                    Text(text = "Restart Quiz")
                }
                Button(onClick = {
                    viewModel.addNotification(Notification(id = 1, message = "$quizTitle finito"))
                    navController.navigate("notifications")
                    navController.navigate(Routes.homeScreen) {
                        popUpTo(Routes.homeScreen) { inclusive = true }
                    }
                }) {
                    Text(text = "Go to Home")
                }
            }
        }
    }