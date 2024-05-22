package com.example.brainbusters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Routes.loginScreen, builder = {
                composable(Routes.loginScreen) {
                    LoginScreen(navController)
                }
                composable(Routes.homeScreen+"/{email}") {
                    val email = it.arguments?.getString("email")    //take the email from the backstack, and pass it to the home screen
                    HomeScreen(navController, email?:"No email")
                }
            })

/*
            BrainBustersTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    Scaffold(
                        topBar = { AppBar(navController) },
                    ) { contentPadding ->
                        NavGraph(navController, modifier = Modifier.padding(contentPadding))
                    }
                }
            }*/
        }
    }
}
/*
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
        color = MaterialTheme.colorScheme.primary,
        fontSize = 18.sp
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BrainBustersTheme {
        Greeting("Android")
    }
}*/
