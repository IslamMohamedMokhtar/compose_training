package com.example.compose.domain.use_case.login

import com.example.compose.data.remote.model.request.LoginRequest

interface LoginUseCase {
    suspend operator fun invoke(loginRequest: LoginRequest): Result<String?>
}