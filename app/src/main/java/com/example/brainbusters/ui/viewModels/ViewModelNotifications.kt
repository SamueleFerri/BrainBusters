package com.example.brainbusters.ui.viewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.brainbusters.Notification

class ViewModelNotifications : ViewModel() {
    private val _notifications = mutableStateListOf<Notification>()
    val notifications: SnapshotStateList<Notification> = _notifications

    fun addNotification(notification: Notification) {
        _notifications.add(notification)
    }
}
