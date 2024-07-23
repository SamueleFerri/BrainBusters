package com.example.brainbusters.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.brainbusters.ui.components.NotificationComponent
import com.example.brainbusters.ui.viewModels.NotificationsViewModel

@Composable
fun NotificationsScreen(navController: NavHostController, viewModel: NotificationsViewModel) {
    LazyColumn (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        items(viewModel.notifications) { notification ->
            NotificationComponent(
                notification = notification,
                onRemoveNotification = { clickedNotification ->
                    viewModel.removeNotification(clickedNotification)
                }
            )
        }
    }
}


