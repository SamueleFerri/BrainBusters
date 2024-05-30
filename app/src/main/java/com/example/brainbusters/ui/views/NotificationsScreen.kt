package com.example.brainbusters.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.brainbusters.ui.components.Notification
import com.example.brainbusters.ui.components.NotificationComponent

@Composable
fun NotificationsScreen(navController: NavHostController) {
    // Lista di notifiche fasulle
    val notifications = listOf(
        Notification(
            title = "Nuova notifica",
            message = "Hai ricevuto un nuovo messaggio.",
            timestamp = "2 ore fa"
        ),
        Notification(
            title = "Promozione speciale",
            message = "Oggi solo, uno sconto del 20% su tutti gli articoli!",
            timestamp = "ieri"
        ),
        Notification(
            title = "Aggiornamento disponibile",
            message = "Una nuova versione dell'app è disponibile per il download.",
            timestamp = "3 giorni fa"
        )
    )

    LazyColumn (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        items(notifications) { notification ->
            NotificationComponent(notification = notification)
        }
    }
}


