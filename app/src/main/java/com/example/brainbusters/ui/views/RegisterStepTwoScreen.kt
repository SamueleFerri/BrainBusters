package com.example.brainbusters.ui.views


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.brainbusters.Routes
import com.example.brainbusters.data.entities.User
import com.example.brainbusters.data.viewmodels.RegistrationViewModel
import kotlinx.coroutines.launch

@Composable
fun RegisterStepTwoScreen(
    navController: NavController,
<<<<<<< HEAD
    profilePictureUri: Uri?,
    email: String,
    username: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onRegister: () -> Unit,
    onUsernameChange: (String) -> Unit,
) {
    val context = LocalContext.current
=======
    registrationViewModel: RegistrationViewModel,
    onRegistrationSuccessful: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    // Get data from the shared ViewModel
    val profilePictureUri by registrationViewModel.profilePictureUri.collectAsState()
    val firstName by registrationViewModel.firstName.collectAsState()
    val lastName by registrationViewModel.lastName.collectAsState()
    val username by registrationViewModel.username.collectAsState()
    val position by registrationViewModel.position.collectAsState()
>>>>>>> f0c589755a7f82f7fa92bb46625664d64c36bca9

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Create Account - Step 2", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
<<<<<<< HEAD
            value = username,
            onValueChange = onUsernameChange,
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
=======
            value = registrationViewModel.email.value,
            onValueChange = { registrationViewModel.setEmail(it) },
>>>>>>> f0c589755a7f82f7fa92bb46625664d64c36bca9
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = registrationViewModel.password.value,
            onValueChange = { registrationViewModel.setPassword(it) },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { /* No-op, username is updated automatically */ },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false // Disable manual editing of the username
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    // Create a User object with the provided data
                    val user = User(
                        username = username,
                        email = registrationViewModel.email.value,
                        password = registrationViewModel.password.value,
                        firstName = firstName,
                        lastName = lastName,
                        position = position,
                        profilePictureUri = profilePictureUri?.toString()
                    )
                    // Insert the user into the repository
                    registrationViewModel.registerUser()
                    // Navigate to the next screen
                    onRegistrationSuccessful()
                }
            },
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text(text = "Register")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate(Routes.loginScreen) }) {
            Text(text = "Already have an account? Log in", fontSize = 16.sp)
        }
    }
}