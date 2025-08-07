package com.example.compose.ui.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.ui.util.LocalNavController
import com.example.compose.ui.util.NavigationEnum
import com.example.compose.util.SharedPreferencesHelper
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(modifier: Modifier = Modifier, sharedPreferencesHelper: SharedPreferencesHelper){
    val navController = LocalNavController.current
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        visible = true
        delay(2000)
        visible = false
        delay(500)
        val route = if(sharedPreferencesHelper.token?.isNotEmpty() == true) NavigationEnum.DASHBOARD.route else NavigationEnum.LOGIN.route
        navController.navigate(route){
            popUpTo(NavigationEnum.SPLASH.route) { inclusive = true }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(1000)) + scaleIn(initialScale = 0.8f),
            exit = fadeOut(animationSpec = tween(300)) + scaleOut(targetScale = 1.1f)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(id = R.drawable.day_off_icon), contentDescription = "Logo", modifier = Modifier.size(150.dp))
                Text(
                    text = "My App",
                    fontSize = 40.sp,
                )
            }
        }
    }
}