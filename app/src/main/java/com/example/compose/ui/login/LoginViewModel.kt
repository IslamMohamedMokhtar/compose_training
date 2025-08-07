package com.example.compose.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.data.remote.model.request.LoginRequest
import com.example.compose.domain.use_case.login.LoginUseCase
import com.example.compose.util.SharedPreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onUsernameChange(newUsername: String) {
        _uiState.update { it.copy(username = newUsername) }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update { it.copy(password = newPassword) }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun onLoginClick(onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = loginUseCase(LoginRequest(_uiState.value.username, _uiState.value.password))
            withContext(Dispatchers.Main){
                if(response.isSuccess) {
                    val temp = response.getOrNull()
                    sharedPreferencesHelper.token = temp
                    onSuccess.invoke()
                }
            }
        }
    }
}
