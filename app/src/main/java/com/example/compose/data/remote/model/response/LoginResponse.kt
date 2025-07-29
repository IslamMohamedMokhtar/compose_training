package com.example.compose.data.remote.model.response

class LoginResponse : BaseResponse<LoginResponseData>()

data class LoginResponseData(
    val token: String?
)