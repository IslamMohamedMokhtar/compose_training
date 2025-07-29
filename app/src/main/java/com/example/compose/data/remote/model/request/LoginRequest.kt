package com.example.compose.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("UserName")
    val userName: String,
    @SerializedName("Password")
    val password: String
)