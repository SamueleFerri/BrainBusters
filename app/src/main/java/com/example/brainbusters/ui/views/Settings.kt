package com.example.brainbusters.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.brainbusters.ui.viewModels.UserViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Settings(navController: NavController) {
    var showChangePasswordFields by remember { mutableStateOf(false) }
    var showDeleteAccountDialog by remember { mutableStateOf(false) }
    val userViewModel = koinViewModel<UserViewModel>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title and Divider
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.primary
            )
        }


        Spacer(modifier = Modifier.height(24.dp))

        // Change Password Button and Fields
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { showChangePasswordFields = !showChangePasswordFields },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(50))
            ) {
                Text(text = "Change Password")
            }

            if (showChangePasswordFields) {
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Old Password") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("New Password") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Delete Account Button
        Button(
            onClick = { showDeleteAccountDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(50)),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text(text = "Delete Account", color = MaterialTheme.colorScheme.onError)
        }

        // Delete Account Confirmation Dialog
        if (showDeleteAccountDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteAccountDialog = false },
                title = { Text("Delete Account") },
                text = { Text("Are you sure you want to delete your account? This action cannot be undone.") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDeleteAccountDialog = false
                            // Handle account deletion
                        }
                    ) {
                        Text("Confirm", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDeleteAccountDialog = false }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
