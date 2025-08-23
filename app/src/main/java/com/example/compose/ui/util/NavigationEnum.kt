package com.example.compose.ui.util

import android.net.Uri

enum class NavigationEnum(val route: String) {
    LOGIN("/login_screen"),
    SPLASH("/splash_screen"),
    DASHBOARD("/dashboard_screen"),
    CALENDAR("/calendar_screen"),
    BALANCE("/balance_screen"),
    EMPLOYEE("/employee_screen"),
    SETTINGS("/settings_screen"),
    IMAGE_PREVIEW("/image_preview_screen/{url}");
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route.substringBefore("/{")) // remove the placeholder part
            args.forEach { arg ->
                append("/${Uri.encode(arg)}") // encode for safety
            }
        }
    }

}