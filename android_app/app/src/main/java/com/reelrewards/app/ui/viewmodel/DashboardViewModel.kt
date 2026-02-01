package com.reelrewards.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reelrewards.app.data.remote.ReelRewardsApi
import com.reelrewards.app.ui.components.DuelInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.reelrewards.app.data.remote.DuelAcceptRequest
import com.reelrewards.app.data.remote.DuelInviteRequest
import com.reelrewards.app.data.remote.VideoCompleteRequest

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val api: ReelRewardsApi
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    fun loadDashboardData(token: String) {
        viewModelScope.launch {
            try {
                // Keep loading state only if it was already loading or error, 
                // to avoid flickering if verifying other things
                if(_uiState.value !is DashboardUiState.Success) {
                    _uiState.value = DashboardUiState.Loading
                }
                
                val user = api.getUserProfile("Bearer $token")
                val duels = api.getActiveDuels("Bearer $token")
                val pendingInvites = api.getPendingInvites("Bearer $token")

                _uiState.value = DashboardUiState.Success(
                    userName = user.name,
                    pointsBalance = user.points_balance,
                    soloStreakDays = user.solo_streak_days,
                    activeDuels = duels.map { 
                        DuelInfo(it.partner_name ?: "Friend", it.days, it.multiplier_level.toDouble()) 
                    },
                    pendingInvites = pendingInvites
                )
            } catch (e: Exception) {
                _uiState.value = DashboardUiState.Error(e.message ?: "Failed to load dashboard")
            }
        }
    }

    fun completeVideo(token: String, videoId: String, multiplier: Int) {
        viewModelScope.launch {
            try {
                api.completeVideo("Bearer $token", VideoCompleteRequest(videoId, multiplier))
                loadDashboardData(token) // Refresh balance immediately
            } catch (e: Exception) {
                // Log error
            }
        }
    }

    fun sendInvite(token: String, phone: String) {
        viewModelScope.launch {
             try {
                 api.sendDuelInvite("Bearer $token", DuelInviteRequest(phone))
                 // Maybe show toast or success message
             } catch (e: Exception) {
                 // Handle error
             }
        }
    }

    fun acceptInvite(token: String, inviteId: String) {
        viewModelScope.launch {
            try {
                api.acceptDuelInvite("Bearer $token", DuelAcceptRequest(inviteId))
                loadDashboardData(token) // Refresh to see new active duel
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

sealed class DashboardUiState {
    object Loading : DashboardUiState()
    data class Success(
        val userName: String,
        val pointsBalance: Int,
        val soloStreakDays: Int,
        val activeDuels: List<DuelInfo>,
        val pendingInvites: List<com.reelrewards.app.data.remote.DuelInviteResponse> = emptyList()
    ) : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}
