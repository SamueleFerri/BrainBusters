package com.example.brainbusters

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.brainbusters.data.viewmodels.RegistrationViewModel
import com.example.brainbusters.ui.views.HomeScreen
import com.example.brainbusters.ui.views.LoginScreen
import com.example.brainbusters.ui.views.NotificationsScreen
import com.example.brainbusters.ui.views.Profile
import com.example.brainbusters.ui.views.QuizScreen
import com.example.brainbusters.ui.views.RegisterStepOneScreen
import com.example.brainbusters.ui.views.RegisterStepTwoScreen
import com.example.brainbusters.ui.views.ScoreScreen
import com.example.brainbusters.ui.views.Scoreboard
import com.example.brainbusters.ui.views.Settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrainBustersNavigation(navController: NavController, registrationViewModel: RegistrationViewModel) {

    var isLoggedIn by remember { mutableStateOf(false) }
    val selected = remember { mutableStateOf(Icons.Default.Home) }

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
            navController = navController as NavHostController,
            startDestination = if (isLoggedIn) Routes.homeScreen else Routes.loginScreen,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Routes.loginScreen) {
                LoginScreen(
                    navController = navController,
                    onLoginSuccessful = { isLoggedIn = true }
                )
            }
            composable(Routes.registerStepOne) {
                RegisterStepOneScreen(
                    navController = navController,
                    registrationViewModel = registrationViewModel
                )
            }
            composable(Routes.registerStepTwo) {
                RegisterStepTwoScreen(
                    navController = navController,
                    registrationViewModel = registrationViewModel,
                    onRegistrationSuccessful = {
                        isLoggedIn = true
                        navController.navigate(Routes.homeScreen)
                    }
                )
            }
            composable(Routes.homeScreen) { HomeScreen(navController = navController) }
            composable(Routes.scoreboard) { Scoreboard(navController = navController) }
            composable(Routes.profile) { Profile(navController = navController) }
            composable(Routes.settings) { Settings(navController = navController) }
            composable(Routes.notifications) { NotificationsScreen(navController = navController) }
            composable(
                route = "quizScreen/{quizTitle}",
                arguments = listOf(navArgument("quizTitle") { type = NavType.StringType })
            ) { backStackEntry ->
                val quizTitle = backStackEntry.arguments?.getString("quizTitle") ?: "Quiz"
                QuizScreen(navController = navController, quizTitle = quizTitle)
            }
            composable("quiz_screen/restart") {
                QuizScreen(navController = navController, quizTitle = "Quiz")
            }
            composable("score_screen/{score}") { backStackEntry ->
                val score = backStackEntry.arguments?.getString("score")?.toInt() ?: 0
                ScoreScreen(navController = navController, score = score)
            }
        }
    }
}
