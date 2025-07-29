package com.example.compose.data.repository.login

import com.example.compose.data.remote.model.request.LoginRequest

interface LoginRepository {
    suspend operator fun invoke(loginRequest: LoginRequest): Result<String?>
}