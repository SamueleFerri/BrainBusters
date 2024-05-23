package com.example.brainbusters

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.brainbusters.ui.theme.GreenJC

@Composable
fun BrainBustersNavigation() {
    val navController = rememberNavController()
    var isLoggedIn by remember { mutableStateOf(false) }  // Manage logged-in state here
    val selected = remember {
        mutableStateOf(Icons.Default.Home)
    }

    Scaffold(
        bottomBar = {
            if (isLoggedIn) {
                BottomAppBar(
                    containerColor = GreenJC
                ) {
                    IconButton(onClick = {
                        selected.value = Icons.Default.Home
                        navController.navigate(Routes.homeScreen) {
                            popUpTo(0)  //avoid multiple back buttons click
                        }
                    }, modifier = Modifier.weight(1f)) {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = if (selected.value == Icons.Default.Home) Color.White else Color.DarkGray
                        )
                    }

                    IconButton(onClick = {
                        selected.value = Icons.Default.Star
                        navController.navigate(Routes.scoreboard) {
                            popUpTo(0)  //avoid multiple back buttons click
                        }
                    }, modifier = Modifier.weight(1f)) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = if (selected.value == Icons.Default.Star) Color.White else Color.DarkGray
                        )
                    }

                    IconButton(onClick = {
                        selected.value = Icons.Default.Settings
                        navController.navigate(Routes.settings) {
                            popUpTo(0)  //avoid multiple back buttons click
                        }
                    }, modifier = Modifier.weight(1f)) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = if (selected.value == Icons.Default.Settings) Color.White else Color.DarkGray
                        )
                    }

                    IconButton(onClick = {
                        selected.value = Icons.Default.AccountCircle
                        navController.navigate(Routes.profile) {
                            popUpTo(0)  //avoid multiple back buttons click
                        }
                    }, modifier = Modifier.weight(1f)) {
                        Icon(
                            Icons.Default.AccountCircle,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = if (selected.value == Icons.Default.AccountCircle) Color.White else Color.DarkGray
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
            composable(Routes.loginScreen) { LoginScreen(navController = navController, onLoginSuccessful = { isLoggedIn = true })}
            composable(Routes.homeScreen) { HomeScreen(navController = navController) }
            composable(Routes.scoreboard) { Scoreboard(navController = navController) }
            composable(Routes.profile) { Profile(navController = navController) }
            composable(Routes.settings) { Settings(navController = navController) }
        }
    }
}