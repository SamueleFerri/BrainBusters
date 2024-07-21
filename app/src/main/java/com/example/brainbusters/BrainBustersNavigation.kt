package com.example.brainbusters

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.brainbusters.Routes
import com.example.brainbusters.ui.viewModels.*
import com.example.brainbusters.ui.views.*
import kotlinx.coroutines.flow.firstOrNull
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
suspend fun BrainBustersNavigation() {
    val navController = rememberNavController()
    val notificationsViewModel: NotificationsViewModel = viewModel()
    val userViewModel = koinViewModel<UserViewModel>()
    val quizViewModel = koinViewModel<QuizViewModel>()
    val careerViewModel = koinViewModel<CareerViewModel>()
    val questionViewModel = koinViewModel<QuestionViewModel>()
    val quizDoneViewModel = koinViewModel<QuizDoneViewModel>()

    var showErrorDialog by remember { mutableStateOf(false) }
    var isLoggedIn by rememberSaveable { mutableStateOf(false) }
    val selected = remember { mutableStateOf(Icons.Default.Home) }

    var profilePictureUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var username by rememberSaveable { mutableStateOf("") }
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var position by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            if (isLoggedIn) {
                TopAppBar(
                    title = {
                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.logo_brain_busters),
                                contentDescription = "Brain Busters Logo",
                                modifier = Modifier.size(30.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "BrainBusters",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary,
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            navController.navigate(Routes.notifications) {
                                popUpTo(Routes.homeScreen) { inclusive = false }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (isLoggedIn) {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    IconButton(onClick = {
                        selected.value = Icons.Default.Home
                        navController.navigate(Routes.homeScreen) {
                            popUpTo(Routes.homeScreen) { inclusive = true }
                        }
                    }, modifier = Modifier.weight(1f)) {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = if (selected.value == Icons.Default.Home) MaterialTheme.colorScheme.onPrimary else Color.Gray
                        )
                    }

                    IconButton(onClick = {
                        selected.value = Icons.Default.Star
                        navController.navigate(Routes.scoreboard) {
                            popUpTo(Routes.homeScreen) { inclusive = true }
                        }
                    }, modifier = Modifier.weight(1f)) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = if (selected.value == Icons.Default.Star) MaterialTheme.colorScheme.onPrimary else Color.Gray
                        )
                    }

                    IconButton(onClick = {
                        selected.value = Icons.Default.Settings
                        navController.navigate(Routes.settings) {
                            popUpTo(Routes.homeScreen) { inclusive = true }
                        }
                    }, modifier = Modifier.weight(1f)) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = if (selected.value == Icons.Default.Settings) MaterialTheme.colorScheme.onPrimary else Color.Gray
                        )
                    }

                    IconButton(onClick = {
                        selected.value = Icons.Default.AccountCircle
                        navController.navigate(Routes.profile) {
                            popUpTo(Routes.homeScreen) { inclusive = true }
                        }
                    }, modifier = Modifier.weight(1f)) {
                        Icon(
                            Icons.Default.AccountCircle,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = if (selected.value == Icons.Default.AccountCircle) MaterialTheme.colorScheme.onPrimary else Color.Gray
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = if (isLoggedIn) Routes.homeScreen else Routes.loginScreen,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Routes.loginScreen) {
                LaunchedEffect(Unit) {
                    isLoggedIn = false
                }
                LoginScreen(
                    navController = navController,
                    onLoginSuccessful = {
                        isLoggedIn = true
                    }
                )
            }
            composable(Routes.registerStepOne) {
                RegisterStepOneScreen(
                    navController = navController,
                    profilePictureUri = profilePictureUri,
                    onProfilePictureChange = { profilePictureUri = it },
                    firstName = firstName,
                    onFirstNameChange = { firstName = it },
                    lastName = lastName,
                    onLastNameChange = { lastName = it },
                    position = position,
                    onPositionChange = { position = it },
                    onProceed = { navController.navigate(Routes.registerStepTwo) }
                )
            }
            composable(Routes.registerStepTwo) {
                RegisterStepTwoScreen(
                    navController = navController,
                    profilePictureUri = profilePictureUri,
                    email = email,
                    onEmailChange = { email = it },
                    password = password,
                    onPasswordChange = { password = it },
                    username = username,
                    onUsernameChange = { username = it },
                    onRegister = {
                        // Handle registration logic here
                        val success = userViewModel.actions.register(
                            name = firstName,
                            surname = lastName,
                            username = username,
                            email = email,
                            password = password,
                            image = profilePictureUri.toString(),
                            position = position
                        )
                        if (success) {
                            isLoggedIn = true
                            navController.navigate(Routes.homeScreen)
                        } else {
                            isLoggedIn = false
                            showErrorDialog = true
                        }
                    }
                )
            }

            composable(Routes.homeScreen) {
                HomeScreen(
                    navController = navController,
                    userState = userViewModel.state.collectAsStateWithLifecycle(),
                    userAction = userViewModel.actions,
                    quizViewModel = quizViewModel
                )
            }
            composable(Routes.scoreboard) { Scoreboard(navController = navController) }
            composable(Routes.profile) { Profile(navController = navController) }
            composable(Routes.settings) { Settings(navController = navController) }
            composable(Routes.notifications) { NotificationsScreen(navController = navController, notificationsViewModel) }

            composable(
                route = "quizScreen/{quizId}/{quizTitle}",
                arguments = listOf(
                    navArgument("quizId") { type = NavType.IntType },
                    navArgument("quizTitle") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val quizId = backStackEntry.arguments?.getInt("quizId") ?: return@composable
                val quizTitle = backStackEntry.arguments?.getString("quizTitle") ?: return@composable
                QuizScreen(
                    navController = navController,
                    quizId = quizId,
                    quizTitle = quizTitle,
                    questionViewModel = questionViewModel,
                    notificationsViewModel = notificationsViewModel, // Pass NotificationsViewModel
                    quizDoneViewModel = quizDoneViewModel
                )
            }
            composable("quizScreen/restart") {
                QuizScreen(
                    navController = navController,
                    quizId = 0, // Provide a default or handle as needed
                    quizTitle = "Quiz",
                    questionViewModel = questionViewModel,
                    notificationsViewModel = notificationsViewModel, // Pass NotificationsViewModel
                    quizDoneViewModel = quizDoneViewModel
                )
            }
            composable(
                route = "scoreScreen/{score}/{quizTitle}",
                arguments = listOf(
                    navArgument("score") { type = NavType.IntType },
                    navArgument("quizTitle") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val score = backStackEntry.arguments?.getInt("score") ?: return@composable
                val quizTitle = backStackEntry.arguments?.getString("quizTitle") ?: return@composable
                ScoreScreen(
                    navController = navController,
                    score = score,
                    quizTitle = quizTitle
                )
            }
        }
        // Dialog di errore per la registrazione fallita
        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                title = { Text("Registration Error") },
                text = { Text("There was an error during registration. Please try again.") },
                confirmButton = {
                    Button(
                        onClick = { showErrorDialog = false }
                    ) {
                        Text("OK")
                    }
                }
            )
        }
    }
}