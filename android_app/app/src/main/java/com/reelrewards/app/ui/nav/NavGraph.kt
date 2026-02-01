package com.reelrewards.app.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.reelrewards.app.ui.screens.DashboardScreen
import com.reelrewards.app.ui.viewmodel.DashboardUiState
import com.reelrewards.app.ui.screens.LoginScreen
import com.reelrewards.app.ui.viewmodel.DashboardViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.reelrewards.app.ui.theme.FireOrange
import com.reelrewards.app.ui.theme.PremiumBlack
import com.reelrewards.app.ui.viewmodel.LoginUiState
import com.reelrewards.app.ui.viewmodel.LoginViewModel

import com.reelrewards.app.ui.screens.CouponsScreen
import com.reelrewards.app.ui.viewmodel.CouponsViewModel
import com.reelrewards.app.ui.screens.ProfileScreen
import com.reelrewards.app.ui.viewmodel.ProfileViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Dashboard : Screen("dashboard/{token}") {
        fun createRoute(token: String) = "dashboard/$token"
    }
    object Coupons : Screen("coupons/{token}") {
        fun createRoute(token: String) = "coupons/$token"
    }
    object Profile : Screen("profile/{token}") {
        fun createRoute(token: String) = "profile/$token"
    }
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            val state by loginViewModel.uiState.collectAsState()

            // Effect to navigate on success
            LaunchedEffect(state) {
                if (state is LoginUiState.Success) {
                    val token = (state as LoginUiState.Success).token
                    navController.navigate(Screen.Dashboard.createRoute(token)) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            }

            LoginScreen(
                onLoginClick = { phone -> loginViewModel.login(phone) },
                onGoogleLoginClick = { loginViewModel.loginWithGoogle() },
                isLoading = state is LoginUiState.Loading,
                errorMessage = (state as? LoginUiState.Error)?.message
            )
        }

        composable(
            route = Screen.Dashboard.route,
            arguments = listOf(navArgument("token") { type = NavType.StringType })
        ) { backStackEntry ->
            val dashboardViewModel: DashboardViewModel = hiltViewModel() 
            val token = backStackEntry.arguments?.getString("token") ?: ""

            LaunchedEffect(token) {
                if(token.isNotEmpty()) {
                    dashboardViewModel.loadDashboardData(token)
                }
            }

            val uiState by dashboardViewModel.uiState.collectAsState()

            when (val state = uiState) {
                is DashboardUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize().background(PremiumBlack),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = FireOrange)
                    }
                }
                is DashboardUiState.Success -> {
                    DashboardScreen(
                        userName = state.userName,
                        pointsBalance = state.pointsBalance,
                        soloStreakDays = state.soloStreakDays,
                        activeDuels = state.activeDuels,
                        pendingInvites = state.pendingInvites,
                        onVideoComplete = { multiplier ->
                             dashboardViewModel.completeVideo(token, "daily_asmr", multiplier)
                        },
                        onRedeemClick = { 
                            navController.navigate(Screen.Coupons.createRoute(token))
                        },
                        onSendInvite = { phone ->
                            dashboardViewModel.sendInvite(token, phone)
                        },
                        onAcceptInvite = { inviteId ->
                            dashboardViewModel.acceptInvite(token, inviteId)
                        },
                        onProfileClick = {
                            navController.navigate(Screen.Profile.createRoute(token))
                        }
                    )
                }
                is DashboardUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize().background(PremiumBlack),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Oops!",
                                color = Color.Red,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = state.message,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 24.dp)
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Button(
                                onClick = { dashboardViewModel.loadDashboardData(token) },
                                colors = ButtonDefaults.buttonColors(containerColor = FireOrange)
                            ) {
                                Text("Retry")
                            }
                        }
                    }
                }
            }
        }

        composable(
            route = Screen.Coupons.route,
            arguments = listOf(navArgument("token") { type = NavType.StringType })
        ) { backStackEntry ->
            val couponsViewModel: CouponsViewModel = hiltViewModel()
            val token = backStackEntry.arguments?.getString("token") ?: ""

            LaunchedEffect(token) {
                if(token.isNotEmpty()) couponsViewModel.loadCoupons(token)
            }

            val uiState by couponsViewModel.uiState.collectAsState()
            val redemptionResult by couponsViewModel.redemptionResult.collectAsState()

            CouponsScreen(
                uiState = uiState,
                redemptonResult = redemptionResult,
                onRedeem = { couponId, upiId -> couponsViewModel.redeemCoupon(token, couponId, upiId) },
                onBackClick = { navController.popBackStack() },
                onClearResult = { couponsViewModel.clearRedemptionResult() }
            )
        }

        composable(
            route = Screen.Profile.route,
            arguments = listOf(navArgument("token") { type = NavType.StringType })
        ) { backStackEntry ->
            val profileViewModel: ProfileViewModel = hiltViewModel()
            val token = backStackEntry.arguments?.getString("token") ?: ""

            LaunchedEffect(token) {
                if(token.isNotEmpty()) profileViewModel.loadProfile(token)
            }

            val uiState by profileViewModel.uiState.collectAsState()

            ProfileScreen(
                uiState = uiState,
                onBackClick = { navController.popBackStack() },
                onLogoutClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
