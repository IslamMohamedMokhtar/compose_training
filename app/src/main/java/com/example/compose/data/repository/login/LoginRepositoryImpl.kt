package com.example.compose.data.repository.login

import com.example.compose.data.remote.api.ApiService
import com.example.compose.data.remote.model.request.LoginRequest
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val apiService: ApiService) : LoginRepository {
    override suspend fun invoke(
        loginRequest: LoginRequest
    ): Result<String?> {
        val response = apiService.login(loginRequest)
        return if (response.isSuccessful) {
            Result.success(response.body()?.data?.token)
        } else {
            Result.failure(Exception("Login failed"))
        }

    }
}