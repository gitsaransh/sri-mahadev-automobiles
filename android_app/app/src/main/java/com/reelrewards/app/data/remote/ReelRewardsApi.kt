package com.reelrewards.app.data.remote

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Body
import com.google.gson.annotations.SerializedName

interface ReelRewardsApi {
    
    @GET("api/user/profile")
    suspend fun getUserProfile(
        @Header("Authorization") token: String
    ): UserResponse

    @POST("api/video/watch-complete")
    suspend fun completeVideo(
        @Header("Authorization") token: String,
        @Body request: VideoCompleteRequest
    ): RewardResponse

    @GET("api/duel/active")
    suspend fun getActiveDuels(
        @Header("Authorization") token: String
    ): List<DuelResponse>
    
    @GET("api/duel/pending")
    suspend fun getPendingInvites(
        @Header("Authorization") token: String
    ): List<DuelInviteResponse>

    @POST("api/duel/invite")
    suspend fun sendDuelInvite(
        @Header("Authorization") token: String,
        @Body request: DuelInviteRequest
    ): DuelInviteSentResponse

    @POST("api/duel/accept")
    suspend fun acceptDuelInvite(
        @Header("Authorization") token: String,
        @Body request: DuelAcceptRequest
    ): DuelStartResponse

    @GET("api/coupons")
    suspend fun getCoupons(
        @Header("Authorization") token: String
    ): List<CouponResponse>

    @POST("api/coupon/redeem")
    suspend fun redeemCoupon(
        @Header("Authorization") token: String,
        @Body request: RedeemRequest
    ): RedeemResponse

    @POST("api/auth/verify-otp")
    suspend fun verifyOtp(
        @Header("Authorization") token: String,
        @Body request: LoginRequest
    ): UserResponse
}

data class LoginRequest(
    val idToken: String,
    val name: String? = null,
    val city: String? = null,
    val referralCode: String? = null
)

data class VideoCompleteRequest(
    val video_id: String,
    val multiplier: Int = 1
)

data class DuelInviteRequest(
    val to_phone: String
)

data class DuelInviteSentResponse(
    val invite_id: String,
    val message: String
)

data class DuelAcceptRequest(
    val invite_id: String
)

data class DuelStartResponse(
    val streak_id: String,
    val message: String
)

data class DuelInviteResponse(
    val id: String,
    val from_uid: String,
    @SerializedName("inviter_name")
    val inviterName: String? = "Unknown Friend",
    val status: String
)

data class CouponResponse(
    val id: String,
    val partner: String,
    val points_cost: Int,
    val description: String,
    val image_url: String? = null
)

data class RedeemRequest(
    val coupon_id: String,
    val upi_id: String? = null
)

data class RedeemResponse(
    val message: String,
    val coupon_code: String,
    val partner: String
)

data class UserResponse(
    val uid: String,
    val name: String,
    val points_balance: Int,
    val solo_streak_days: Int,
    val referral_code: String,
    val phone: String? = null,
    val total_points_earned: Int = 0,
    val city: String? = null
)

data class RewardResponse(
    val points_awarded: Int,
    val new_balance: Int
)

data class DuelResponse(
    val id: String,
    val partner_name: String? = "Friend",
    val days: Int,
    val multiplier_level: Int
)
