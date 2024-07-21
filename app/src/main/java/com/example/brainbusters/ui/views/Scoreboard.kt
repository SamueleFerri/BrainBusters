package com.example.brainbusters.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

    LazyColumn {
        itemsIndexed(scoreboardEntries) { index, entry ->
            ScoreboardItem(entry = entry)
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
fun ScoreboardItem(entry: ScoreboardEntry) {
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
            .padding(vertical = 15.dp, horizontal = 16.dp),
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