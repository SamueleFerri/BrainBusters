package com.example.brainbusters.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// Mock data for demonstration
data class ScoreboardEntry(
    val position: Int,
    val nickname: String,
    val quizzesCompleted: Int
)

@Composable
fun Scoreboard(navController: NavController) {
    // Mock data for demonstration
    val scoreboardData = listOf(
        ScoreboardEntry(1, "LUCONE111", 39),
        ScoreboardEntry(2, "Player2", 18),
        ScoreboardEntry(3, "Player3", 15),
        ScoreboardEntry(4, "Player4", 14),
        ScoreboardEntry(5, "Player5", 13),
        ScoreboardEntry(6, "Player6", 12),
        ScoreboardEntry(7, "Player7", 11),
        ScoreboardEntry(8, "Player8", 10),
        ScoreboardEntry(9, "Player9", 9),
        ScoreboardEntry(1, "LUCONE111", 39) // Placeholder for user's position
    )

    LazyColumn {
        itemsIndexed(scoreboardData) { index, entry ->
            ScoreboardItem(entry = entry)
            // Divider between scoreboard entries
            if (index < scoreboardData.size - 1) {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                )
            }
        }
    }
}

@Composable
fun ScoreboardItem(entry: ScoreboardEntry) {
    val backgroundColor = when (entry.position) {
        1 -> Color(0xFFFFD700) // Gold
        2 -> Color(0xFFC0C0C0) // Silver
        3 -> Color(0xFFCD7F32) // Bronze
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
                    .background(backgroundColor, RoundedCornerShape(8.dp))
                    .padding(5.dp).padding(horizontal = 3.dp),
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
