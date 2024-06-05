package com.example.brainbusters.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.brainbusters.Notification
import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


//da eliminare è solo per le prove
//data class Notification(
//    val title: String,
//    val message: String,
//    val timestamp: String
//)
@Composable
fun NotificationComponent(
    notification: Notification,
    onRemoveNotification: (Notification) -> Unit
) {
    var clickedNotification by remember { mutableStateOf<Notification?>(null) }

    if (clickedNotification != null) {
        // Rimuovi la notifica e reimposta il valore a null
        onRemoveNotification(clickedNotification!!)
        clickedNotification = null
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier
                .clickable {
                    clickedNotification = notification
                }
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = notification.message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = notification.timestamp,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}
