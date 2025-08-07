package com.example.compose.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.R
import com.example.compose.ui.components.CustomInputField
import com.example.compose.ui.components.GradientButton
import com.example.compose.ui.util.LocalNavController
import com.example.compose.ui.util.NavigationEnum

@Composable
fun LoginScreen(modifier: Modifier = Modifier, viewModel: LoginViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 5.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = {

            },
            modifier = Modifier.align(Alignment.Start).size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = "Favorite"
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.day_off_icon),
            contentDescription = "Login Icon",
            modifier = Modifier.size(48.dp),
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text("Login to your account", modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.size(16.dp))

        CustomInputField(
            label = "Username",
            value = state.username,
            onValueChange = viewModel::onUsernameChange,
            placeholder = "john@company.com or +201012345678",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.size(16.dp))

        CustomInputField(
            label = "Enter password",
            value = state.password,
            onValueChange = viewModel::onPasswordChange,
            placeholder = "New password",
            visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = viewModel::togglePasswordVisibility) {
                    Icon(
                        imageVector = if (state.isPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = if (state.isPasswordVisible) "Hide password" else "Show password"
                    )
                }
            }
        )
        Spacer(modifier = Modifier.size(16.dp))
        val gradient = Brush.horizontalGradient(
            colors = listOf(Color(0xFFFD3177), Color(0xFFFF5E5E)) // Green gradient
        )
        val navController = LocalNavController.current
        GradientButton(
            text = "Login",
            onClick = {
                viewModel.onLoginClick {
                    navController.navigate(NavigationEnum.DASHBOARD.route) {
//                        popUpTo(NavigationEnum.LOGIN.route) { inclusive = true }
                    }
                }
            },
            gradient = gradient,
            modifier = Modifier.fillMaxWidth(),
            textColor = MaterialTheme.colorScheme.onPrimary,
            cornerRadius = 50.dp
        )
        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = "Forgot password?",
            modifier = Modifier
                .clickable(onClick = {})
                .padding(8.dp)
        )

    }
}