package com.example.compose.domain.use_case

import com.example.compose.domain.use_case.login.LoginUseCase
import com.example.compose.domain.use_case.login.LoginUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    @Singleton
    abstract fun bindLoginUseCase(
        impl: LoginUseCaseImpl
    ): LoginUseCase
}
