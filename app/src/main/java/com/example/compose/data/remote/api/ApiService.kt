package com.example.compose.data.remote.api

import com.example.compose.data.remote.model.request.LoginRequest
import com.example.compose.data.remote.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/token")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

}