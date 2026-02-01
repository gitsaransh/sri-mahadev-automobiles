package com.reelrewards.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reelrewards.app.data.remote.CouponResponse
import com.reelrewards.app.data.remote.RedeemRequest
import com.reelrewards.app.data.remote.ReelRewardsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CouponsViewModel @Inject constructor(
    private val api: ReelRewardsApi
) : ViewModel() {

    private val _uiState = MutableStateFlow<CouponsUiState>(CouponsUiState.Loading)
    val uiState: StateFlow<CouponsUiState> = _uiState.asStateFlow()

    private val _redemptionResult = MutableStateFlow<RedemptionResult?>(null)
    val redemptionResult: StateFlow<RedemptionResult?> = _redemptionResult.asStateFlow()

    fun loadCoupons(token: String) {
        viewModelScope.launch {
            try {
                _uiState.value = CouponsUiState.Loading
                val coupons = api.getCoupons("Bearer $token")
                _uiState.value = CouponsUiState.Success(coupons)
            } catch (e: Exception) {
                _uiState.value = CouponsUiState.Error(e.message ?: "Failed to fetch coupons")
            }
        }
    }

    fun redeemCoupon(token: String, couponId: String, upiId: String? = null) {
        viewModelScope.launch {
            try {
                val response = api.redeemCoupon("Bearer $token", RedeemRequest(couponId, upiId))
                _redemptionResult.value = RedemptionResult.Success(response.coupon_code)
                // Refresh coupons or user balance if needed
            } catch (e: Exception) {
                _redemptionResult.value = RedemptionResult.Error(e.message ?: "Redemption failed")
            }
        }
    }

    fun clearRedemptionResult() {
        _redemptionResult.value = null
    }
}

sealed class CouponsUiState {
    object Loading : CouponsUiState()
    data class Success(val coupons: List<CouponResponse>) : CouponsUiState()
    data class Error(val message: String) : CouponsUiState()
}

sealed class RedemptionResult {
    data class Success(val code: String) : RedemptionResult()
    data class Error(val message: String) : RedemptionResult()
}
