package com.example.brainbusters.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
//import com.example.brainbusters.NavigationRoute

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val title = when (backStackEntry?.destination?.route) {
        "screen1" -> "Categories"
        "screen2" -> "Quiz"
        else -> "Unknown Screen"
    }

    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (navController.previousBackStackEntry != null) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.AutoMirrored.Outlined.ArrowBack, "Go Back")
                }
            }
        },
    )
}

@Composable
fun Categories(navController: NavHostController) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Button(
            onClick = { navController.navigate(NavigationRoute.Quiz.route) },
            modifier = Modifier.width(LocalConfiguration.current.screenWidthDp.dp / 2)
        ) {
            Text("Quiz")
        }
    }
}

@Composable
fun Quiz(navController: NavHostController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Button(
            onClick = { navController.navigateUp() },
            modifier = Modifier.weight(1F)
        ) {
            Text("Categories")
        }
    }
}
*/