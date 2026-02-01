package com.reelrewards.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reelrewards.app.data.remote.LoginRequest
import com.reelrewards.app.data.remote.ReelRewardsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val api: ReelRewardsApi
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(phone: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                // In a real app, we would verify OTP with Firebase/SMS first to get ID Token.
                // For MVP/Mock backend, we send a dummy token.
                val dummyToken = "mock_token_for_$phone"
                
                // Call backend (mock mode will accept this token)
                api.verifyOtp("Bearer $dummyToken", LoginRequest(idToken = dummyToken, name = "User $phone"))
                
                // If successful, we consider the token valid for future requests
                _uiState.value = LoginUiState.Success(dummyToken) 
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error(e.message ?: "Login Failed")
            }
        }
    }

    fun loginWithGoogle() {
        // Similar logic for Google
        login("GoogleUser")
    }
}

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val token: String) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}
