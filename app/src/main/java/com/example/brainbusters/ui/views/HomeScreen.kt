package com.example.brainbusters.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Preferiti", modifier = Modifier.padding(16.dp))

        QuizCategory(navController = navController, title = "Categoria 1")
        QuizCategory(navController = navController, title = "Categoria 2")
    }
}

@Composable
fun QuizCategory(navController: NavController, title: String) {
    Column(
        modifier = Modifier
            .padding(horizontal = 6.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(count = 10) { index ->
                QuizItem(navController = navController, title = "Quiz $index")
            }
        }
    }
}

@Composable
fun QuizItem(navController: NavController, title: String) {
    Card(
        modifier = Modifier
            .size(120.dp)
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate("quizScreen/$title")
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title)
        }
    }
}
