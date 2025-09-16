package com.example.compose.data.remote

import com.example.compose.data.remote.api.ApiService
import com.example.compose.util.SharedPreferencesHelper
import com.example.retrofitlibrary.NetworkModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://core2.day-off.app/Dayoff-v2/"

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideInterceptor(sharedPreferencesHelper: SharedPreferencesHelper): Interceptor {
        return Interceptor {
            val request = it.request().newBuilder()
                .apply {
                    if (!sharedPreferencesHelper.token.isNullOrEmpty())
                        addHeader("Authorization", "Bearer ${sharedPreferencesHelper.token}")
                }
                .build()
            it.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(authInterceptor: Interceptor): Retrofit {
        val networkModule = NetworkModule(
            baseUrl = BASE_URL,
            interceptors = listOf(authInterceptor)
        )
        return networkModule.createRetrofit()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}