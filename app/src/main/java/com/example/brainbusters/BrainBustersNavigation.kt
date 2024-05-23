package com.example.brainbusters

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun BrainBustersNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.loginScreen, builder = {
        composable(Routes.loginScreen) {
            LoginScreen(navController)
        }
        composable(Routes.homeScreen+"/{email}") {
            val email = it.arguments?.getString("email")    //take the email from the backstack, and pass it to the home screen
            HomeScreen(navController, email?:"No email")
        }
        composable(Routes.profile){
            Profile(navController)
        }
        composable(Routes.settings){
            Settings(navController)
        }
        composable(Routes.scoreboard){
            Scoreboard(navController)
        }
    })
}
