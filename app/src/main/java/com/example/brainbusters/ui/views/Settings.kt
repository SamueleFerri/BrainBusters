package com.example.brainbusters.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.brainbusters.Routes
import com.example.brainbusters.ui.viewModels.UserViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.androidx.compose.koinViewModel

@Composable
fun Settings(navController: NavController) {
    var showChangePasswordFields by remember { mutableStateOf(false) }
    var showDeleteAccountDialog by remember { mutableStateOf(false) }
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val userViewModel = koinViewModel<UserViewModel>()
    val userEmail = UserViewModel.getEmail()
    var passwordChangedSuccessfully by remember { mutableStateOf(false) }

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
                    value = oldPassword,
                    onValueChange = { oldPassword = it },
                    label = { Text("Old Password") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text("New Password") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val success = runBlocking {
                            userViewModel.actions.changePassword(
                                oldPassword = oldPassword,
                                newPassword = newPassword
                            )
                        }
                        if (success) {
                            passwordChangedSuccessfully = true
                            oldPassword = ""
                            newPassword = ""
                        } else {
                            errorMessage = "Failed to change password. Please check your old password and try again."
                            showErrorDialog = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(50))
                ) {
                    Text(text = "Confirm Change")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Delete Account Button
        Button(
            onClick = {
                runBlocking {
                    userViewModel.actions.removeUser(userViewModel.actions.getRepository().getUserIdByEmail(userEmail).first())
                }
                showDeleteAccountDialog = true
            },
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

        if (passwordChangedSuccessfully) {
            LaunchedEffect(key1 = true) {
                // Delay per mostrare il messaggio di successo per un breve periodo
                delay(3000) // Tempo in millisecondi (ad esempio 3000 per 3 secondi)
                passwordChangedSuccessfully = false
            }
            AlertDialog(
                onDismissRequest = { passwordChangedSuccessfully = false },
                title = { Text("Success") },
                text = { Text("Password changed successfully") },
                confirmButton = {
                    TextButton(
                        onClick = { passwordChangedSuccessfully = false }
                    ) {
                        Text("OK")
                    }
                }
            )
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
                            navController.navigate(Routes.loginScreen)
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

        // Error Dialog
        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                title = { Text("Error") },
                text = { Text(errorMessage) },
                confirmButton = {
                    TextButton(
                        onClick = { showErrorDialog = false }
                    ) {
                        Text("OK")
                    }
                }
            )
        }
    }
}