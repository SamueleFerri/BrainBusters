package com.example.brainbusters.ui.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.brainbusters.R
import com.example.brainbusters.Routes

@Composable
fun LoginScreen(navController: NavController, onLoginSuccessful: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_brain_busters),
            contentDescription = "Login image",
            modifier = Modifier.size(200.dp)
        )

        Text(text = "Welcome back", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Login to your account", fontSize = 16.sp, fontWeight = FontWeight.Normal)
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email address") }
        )

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 16.dp),
            onClick = {
                Log.i("Credential", "Email: $email Password: $password")
                onLoginSuccessful()
                navController.navigate(Routes.homeScreen)
            }
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = { }) {
            Text(text = "Forgot password?", fontSize = 16.sp, fontWeight = FontWeight.Normal)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Don't have an account?", fontSize = 16.sp, fontWeight = FontWeight.Normal)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Create account",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    navController.navigate(Routes.registerScreen)
                }
            )
        }
    }
}
