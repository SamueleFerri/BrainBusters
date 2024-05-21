package com.example.brainbusters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.brainbusters.ui.AppBar
import com.example.brainbusters.ui.Categories
import com.example.brainbusters.ui.Quiz
import com.example.brainbusters.ui.theme.BrainBustersTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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
            }
        }
    }
}

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
}

sealed class NavigationRoute(
    val route: String
) {
    data object Categories : NavigationRoute("categories")
    data object Quiz : NavigationRoute("quiz")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Categories.route,
        modifier
    ) {
        with(NavigationRoute.Categories) {
            composable(route) {
                Categories(navController)
            }
        }
        with(NavigationRoute.Quiz) {
            composable(route) {
                Quiz(navController)
            }
        }
    }
}
