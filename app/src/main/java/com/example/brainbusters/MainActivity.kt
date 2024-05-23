package com.example.brainbusters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.brainbusters.ui.theme.BrainBustersTheme
import com.example.brainbusters.ui.theme.GreenJC

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BrainBustersNavigation()


            BrainBustersTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavBar()
                }
            }
        }
    }
}

@Composable
fun NavBar(){
    val navController = rememberNavController()
    val context = LocalContext.current.applicationContext
    val selected = remember {
        mutableStateOf(Icons.Default.Home)
    }

    Scaffold (
        bottomBar = {
            BottomAppBar(
                containerColor = GreenJC
            ){
                IconButton(onClick = {
                    selected.value = Icons.Default.Home
                    navController.navigate(Routes.homeScreen){
                        popUpTo(0)  //avoid multiple back buttons click
                    }
                }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Home, contentDescription = null, modifier = Modifier.size(26.dp),
                        tint = if(selected.value == Icons.Default.Home) Color.White else Color.DarkGray)
                }

                IconButton(onClick = {
                    selected.value = Icons.Default.Settings
                    navController.navigate(Routes.settings){
                        popUpTo(0)  //avoid multiple back buttons click
                    }
                }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Settings, contentDescription = null, modifier = Modifier.size(26.dp),
                        tint = if(selected.value == Icons.Default.Settings) Color.White else Color.DarkGray)
                }

                IconButton(onClick = {
                    selected.value = Icons.Default.Star
                    navController.navigate(Routes.scoreboard){
                        popUpTo(0)  //avoid multiple back buttons click
                    }
                }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(26.dp),
                        tint = if(selected.value == Icons.Default.Star) Color.White else Color.DarkGray)
                }


                IconButton(onClick = {
                    selected.value = Icons.Default.AccountCircle
                    navController.navigate(Routes.profile){
                        popUpTo(0)  //avoid multiple back buttons click
                    }
                }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.AccountCircle, contentDescription = null, modifier = Modifier.size(26.dp),
                        tint = if(selected.value == Icons.Default.AccountCircle) Color.White else Color.DarkGray)
                }
            }
        }
    ){
            paddingValues ->
        NavHost(navController = navController, startDestination = Routes.homeScreen, modifier = Modifier.padding(paddingValues)) {
            composable(Routes.homeScreen){ HomeScreen(navController = navController, email = "")}
            composable(Routes.scoreboard){ Scoreboard(navController = navController)}
            composable(Routes.profile){ Profile(navController = navController)}
            composable(Routes.settings){ Settings(navController = navController)}
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
