package com.example.compose.ui.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.ui.theme.ComposeTheme

@Composable
fun SettingScreen(){
    Column {
        CompanyHeaderView()
    }
}

@Preview(showBackground = true)
@Composable
fun SettingScreenPreview(){
    ComposeTheme {
        SettingScreen()
    }
}