package com.example.compose.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.compose.R
import com.example.compose.ui.components.CustomInputField

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = {

            },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = "Favorite"
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.day_off_icon),
            contentDescription = "Login Icon",
            modifier = Modifier.size(96.dp),
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text("Login to your account", modifier = Modifier.align(Alignment.CenterHorizontally))
        var text by remember { mutableStateOf("") }

        Spacer(modifier = Modifier.size(16.dp))

        CustomInputField(
            label = "Username",
            value = text,
            onValueChange = { text = it },
            placeholder = "Enter your username",
            modifier = Modifier.fillMaxWidth()
        )

    }
}