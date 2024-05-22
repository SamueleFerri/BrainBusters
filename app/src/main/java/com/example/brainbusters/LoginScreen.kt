package com.example.brainbusters

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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

@Composable
fun LoginScreen(navController: NavController){

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.panda), contentDescription = "Login image",
            modifier = Modifier.size(200.dp))

        Text(text = "Welcome back", fontSize = 28. sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height((4.dp)))

        Text(text = "Login to your account", fontSize = 16. sp, fontWeight = FontWeight.Normal)
        Spacer(modifier = Modifier.height((30.dp)))
        OutlinedTextField(value = email, onValueChange = {
            email = it
        }, label = {
            Text(text = "Email address")
        })

        Spacer(modifier = Modifier.height((16.dp)))
        OutlinedTextField(value = password, onValueChange = {
            password = it
        }, label = {
            Text(text = "Password")
        }, visualTransformation = PasswordVisualTransformation())

        Spacer(modifier = Modifier.height((16.dp)))
        Button(onClick = {
            Log.i("Credential", "Email: $email Password: $password")
            navController.navigate(Routes.homeScreen+"/$email")
        }){
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height((16.dp)))
        TextButton(onClick = { }){
            Text(text = "Forgot password?", fontSize = 16. sp, fontWeight = FontWeight.Normal)
        }

        /*Text(text = "Forgot password?", fontSize = 16. sp, fontWeight = FontWeight.Normal, modifier = Modifier.clickable {

        })*/

        Spacer(modifier = Modifier.height((4.dp)))
        Text(text = "Or sing with", fontSize = 16. sp, fontWeight = FontWeight.Normal)
        Spacer(modifier = Modifier.height((4.dp)))

        Row (
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(painter = painterResource(id = R.drawable.google), contentDescription = "Google image",
                modifier = Modifier
                    .size(60.dp)
                    .clickable {
                        //Facebook clicked
                    }
            )

            Image(painter = painterResource(id = R.drawable.f), contentDescription = "Facebook",
                modifier = Modifier
                    .size(60.dp)
                    .clickable {
                        //Google clicked
                    }
            )

            Image(painter = painterResource(id = R.drawable.x), contentDescription = "X",
                modifier = Modifier
                    .size(60.dp)
                    .clickable {
                        //X clicked
                    }
            )
        }
    }
}
