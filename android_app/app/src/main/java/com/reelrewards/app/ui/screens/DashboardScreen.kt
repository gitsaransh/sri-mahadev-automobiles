package com.reelrewards.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reelrewards.app.data.remote.DuelInviteResponse
import com.reelrewards.app.ui.components.BannerAd
import com.reelrewards.app.ui.components.DuelInfo
import com.reelrewards.app.ui.components.DuelInviteDialog
import com.reelrewards.app.ui.components.SpinWheelDialog
import com.reelrewards.app.ui.components.StreakWidget
import com.reelrewards.app.ui.theme.*

@Composable
fun DashboardScreen(
    userName: String,
    pointsBalance: Int,
    soloStreakDays: Int,
    activeDuels: List<DuelInfo>,
    pendingInvites: List<DuelInviteResponse> = emptyList(),
    onRedeemClick: () -> Unit,
    onVideoComplete: (Int) -> Unit,
    onSendInvite: (String) -> Unit,
    onAcceptInvite: (String) -> Unit,
    onProfileClick: () -> Unit
) {
    var showSpinWheel by remember { mutableStateOf(false) }
    var showInviteDialog by remember { mutableStateOf(false) }

    if (showSpinWheel) {
        SpinWheelDialog(
            onDismiss = { showSpinWheel = false },
            onSpinComplete = { multiplier ->
                onVideoComplete(multiplier)
            }
        )
    }

    if (showInviteDialog) {
        DuelInviteDialog(
            onDismiss = { showInviteDialog = false },
            onSendInvite = onSendInvite
        )
    }

    Scaffold(
        containerColor = PremiumBlack,
        topBar = {
            DashboardTopBar(userName, pointsBalance, onRedeemClick, onProfileClick)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showInviteDialog = true },
                containerColor = FireOrange,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Invite Friend")
            }
        },
        bottomBar = {
            ReelRewardsBottomNav()
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // Streak & Active Duels Widget
            item {
                StreakWidget(soloStreakDays = soloStreakDays, activeDuels = activeDuels)
            }

            // Pending Invites Section
            if (pendingInvites.isNotEmpty()) {
                item {
                    SectionHeader("Pending Invites")
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    ) {
                        pendingInvites.forEach { invite ->
                            InviteCard(invite, onAccept = { onAcceptInvite(invite.id) })
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }

            // Watch & Earn Section
            item {
                SectionHeader("Watch & Earn")
                VideoHeroCard(onVideoClick = { showSpinWheel = true })
            }

            // Progress Section
            item {
                SectionHeader("Your Progress")
                RewardProgressCard(balance = pointsBalance)
            }
            
            // AdMob Banner
            item {
                Spacer(modifier = Modifier.height(24.dp))
                // Ad Container with Placeholder Background
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .height(60.dp)
                        .background(SurfaceGrey.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
                     contentAlignment = Alignment.Center
                ) {
                     Text("Ad Space", color = Color.Gray, fontSize = 10.sp)
                     BannerAd() 
                }
            }
            item {
                Spacer(modifier = Modifier.height(150.dp))
            }
        }
    }
}

@Composable
fun DashboardTopBar(userName: String, balance: Int, onRedeemClick: () -> Unit, onProfileClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(SurfaceGrey)
                    .clickable { onProfileClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = FireOrange)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Hello, $userName",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontSize = 18.sp
                )
                Text(
                    text = "Ready to earn?",
                    style = MaterialTheme.typography.labelSmall,
                    color = White80
                )
            }
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(if (balance >= 100) RewardGold else SurfaceGrey)
                .clickable { onRedeemClick() }
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                "Redeem",
                color = if (balance >= 100) PremiumBlack else White80,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        color = Color.White,
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
    )
}

@Composable
fun VideoHeroCard(onVideoClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 24.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(SurfaceGrey)
            .clickable { onVideoClick() }
    ) {
        // Placeholder for ASMR Video Thumbnail
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))))
        )
        
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(20.dp)
        ) {
            Text("Relaxing ASMR Cooking", color = Color.White, fontWeight = FontWeight.Bold)
            Text("Earn 10 Points • 30s", color = White80, fontSize = 12.sp)
        }

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(50.dp)
                .clip(CircleShape)
                .background(FireOrange),
            contentAlignment = Alignment.Center
        ) {
            Text("▶", color = Color.White, fontSize = 20.sp)
        }
    }
}

@Composable
fun RewardProgressCard(balance: Int) {
    val progress = (balance % 100) / 100f
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(SurfaceGrey)
            .padding(20.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Next Reward: ₹100", color = Color.White, fontSize = 14.sp)
                Text("${balance}/100 pts", color = RewardGold, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(CircleShape),
                color = FireOrange,
                trackColor = Color.White.copy(alpha = 0.1f)
            )
        }
    }
}

@Composable
fun ReelRewardsBottomNav() {
    NavigationBar(
        containerColor = PremiumBlack,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = FireOrange,
                selectedTextColor = FireOrange,
                unselectedIconColor = White80,
                unselectedTextColor = White80,
                indicatorColor = Color.Transparent
            )
        )
        // Add more items (Coupons, Profile) here if needed for visual completeness
    }
}

@Composable
fun InviteCard(invite: DuelInviteResponse, onAccept: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceGrey)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Duel Invite",
                color = RewardGold,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "From: ${invite.inviterName}", 
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
        
        Button(
            onClick = onAccept,
            colors = ButtonDefaults.buttonColors(containerColor = FireOrange),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
             Text("Accept", color = Color.White)
             Spacer(modifier = Modifier.width(4.dp))
             Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
        }
    }
}
