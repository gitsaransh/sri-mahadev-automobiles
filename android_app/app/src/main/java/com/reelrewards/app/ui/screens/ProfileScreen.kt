package com.reelrewards.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reelrewards.app.data.remote.UserResponse
import com.reelrewards.app.ui.theme.*
import com.reelrewards.app.ui.viewmodel.ProfileUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Scaffold(
        containerColor = PremiumBlack,
        topBar = {
            SmallTopAppBar(
                title = { Text("My Profile", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = onLogoutClick) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout", tint = FireOrange)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = PremiumBlack)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (uiState) {
                is ProfileUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = FireOrange
                    )
                }
                is ProfileUiState.Error -> {
                    Text(
                        text = uiState.message,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is ProfileUiState.Success -> {
                    ProfileContent(uiState.user)
                }
            }
        }
    }
}

@Composable
fun ProfileContent(user: UserResponse) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- AVATAR & NAME ---
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Brush.linearGradient(FireGradient)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(60.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = user.name,
            style = Typography.displaySmall,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )
        Text(
            text = user.phone ?: "+91 Unknown",
            style = Typography.bodyLarge,
            color = White80
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- STATS CARDS ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatCard(
                label = "Balance",
                value = "${user.points_balance}",
                color = RewardGold,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                label = "Lifetime Earned",
                value = "${user.total_points_earned}",
                color = FireOrange,
                modifier = Modifier.weight(1f)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // --- REFERRAL CARD ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(SurfaceGrey)
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text("Your Referral Code", color = White80, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        user.referral_code,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                }
                IconButton(onClick = { /* Share Logic */ }) {
                    Icon(Icons.Default.Share, contentDescription = "Share", tint = FireOrange)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))

        // --- APP INFO ---
        Text(
            "Version 1.0.0 (MVP)", 
            color = White80.copy(alpha = 0.3f),
            fontSize = 12.sp
        )
    }
}

@Composable
fun StatCard(label: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceGrey)
            .padding(16.dp)
    ) {
        Column {
            Text(label, color = White80, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, color = color, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}
