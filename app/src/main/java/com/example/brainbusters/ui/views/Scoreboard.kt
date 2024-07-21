package com.example.brainbusters.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.brainbusters.ui.viewModels.ScoreboardViewModel

// Data class for scoreboard entry
data class ScoreboardEntry(
    val position: Int,
    val nickname: String,
    val quizzesCompleted: Int,
    val isCurrentUser: Boolean = false // Aggiungi la proprietà isCurrentUser
)

@Composable
fun Scoreboard(navController: NavController, scoreboardViewModel: ScoreboardViewModel) {
    val scoreboardEntries by scoreboardViewModel.scoreboardEntries.collectAsState(initial = emptyList())
    var selectedEntry by remember { mutableStateOf<ScoreboardEntry?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    // Show dialog if an entry is selected
    if (showDialog && selectedEntry != null) {
        ScoreboardEntryDialog(entry = selectedEntry!!, onDismiss = { showDialog = false })
    }

    LazyColumn {
        itemsIndexed(scoreboardEntries) { index, entry ->
            ScoreboardItem(entry = entry, onClick = {
                selectedEntry = entry
                showDialog = true
            })
            // Divider between scoreboard entries
            if (index < scoreboardEntries.size - 1) {
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                )
            }
        }
    }
}

@Composable
fun ScoreboardItem(entry: ScoreboardEntry, onClick: () -> Unit) {
    // Define colors for different positions
    val goldColor = Color(0xFFFFD700) // Gold
    val silverColor = Color(0xFFC0C0C0) // Silver
    val bronzeColor = Color(0xFFCD7F32) // Bronze

    // Determine the background color of the position box
    val positionBackgroundColor = when (entry.position) {
        1 -> goldColor
        2 -> silverColor
        3 -> bronzeColor
        else -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp, horizontal = 16.dp)
            .clickable { onClick() }, // Make item clickable
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Position
        Box(
            modifier = Modifier
                .width(38.dp)
                .padding(end = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(positionBackgroundColor, RoundedCornerShape(8.dp))
                    .padding(horizontal = 7.dp, vertical = 5.dp)
                    .align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${entry.position}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // Nickname
        Text(
            text = entry.nickname,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )

        // Quizzes Completed
        Text(
            text = "${entry.quizzesCompleted}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.width(64.dp),
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun ScoreboardEntryDialog(entry: ScoreboardEntry, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Details for ${entry.nickname}") },
        text = {
            Column {
                Text(text = "Position: ${entry.position}")
                Text(text = "Nickname: ${entry.nickname}")
                Text(text = "Quizzes Completed: ${entry.quizzesCompleted}")
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}