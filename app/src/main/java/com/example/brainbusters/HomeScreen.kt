package com.example.brainbusters

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(){
    Column {
        Text(text = "home screen")
        Button(onClick = { /*TODO*/ }) {
            Text(text = "go to login")
        }
    }

}