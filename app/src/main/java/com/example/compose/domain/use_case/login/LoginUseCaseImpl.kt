package com.example.compose.domain.use_case.login

import com.example.compose.data.remote.model.request.LoginRequest
import com.example.compose.data.repository.login.LoginRepository
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(private val loginRepository: LoginRepository): LoginUseCase {
    override suspend fun invoke(
        loginRequest: LoginRequest
    ): Result<String?> {
        return loginRepository(loginRequest)
    }
}