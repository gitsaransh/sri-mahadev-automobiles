package com.reelrewards.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.reelrewards.app.data.remote.CouponResponse
import com.reelrewards.app.ui.theme.FireOrange
import com.reelrewards.app.ui.theme.PremiumBlack
import com.reelrewards.app.ui.theme.RewardGold
import com.reelrewards.app.ui.theme.SurfaceGrey
import com.reelrewards.app.ui.theme.Typography
import com.reelrewards.app.ui.theme.White80
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CouponsScreen(
    uiState: com.reelrewards.app.ui.viewmodel.CouponsUiState,
    redemptonResult: com.reelrewards.app.ui.viewmodel.RedemptionResult?,
    onRedeem: (String, String?) -> Unit,
    onBackClick: () -> Unit,
    onClearResult: () -> Unit
) {
    var showUpiDialogForCouponId by remember { mutableStateOf<String?>(null) }
    var isProcessing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    if (redemptonResult != null) {
        RedemptionDialog(result = redemptonResult, onDismiss = onClearResult)
    }
    
    if (isProcessing) {
        Dialog(onDismissRequest = {}) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(PremiumBlack)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = FireOrange)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Processing UPI Payout...", color = Color.White)
                }
            }
        }
    }

    if (showUpiDialogForCouponId != null) {
        UpiDialog(
            onDismiss = { showUpiDialogForCouponId = null },
            onConfirm = { upiId ->
                val couponId = showUpiDialogForCouponId
                showUpiDialogForCouponId = null
                isProcessing = true
                scope.launch {
                    delay(2500) // Fake processing time
                    isProcessing = false
                    if (couponId != null) {
                        onRedeem(couponId, upiId)
                    }
                }
            }
        )
    }

    Scaffold(
        containerColor = PremiumBlack,
        topBar = {
            SmallTopAppBar(
                title = { Text("Rewards Store", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = PremiumBlack)
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when (uiState) {
                is com.reelrewards.app.ui.viewmodel.CouponsUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = FireOrange)
                }
                is com.reelrewards.app.ui.viewmodel.CouponsUiState.Error -> {
                    Text(
                        text = uiState.message,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is com.reelrewards.app.ui.viewmodel.CouponsUiState.Success -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(uiState.coupons) { coupon ->
                            CouponCard(
                                coupon = coupon, 
                                onRedeem = { 
                                    if (coupon.partner.contains("UPI")) {
                                        showUpiDialogForCouponId = coupon.id
                                    } else {
                                        onRedeem(coupon.id, null) 
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UpiDialog(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var upiId by remember { mutableStateOf("") }
    
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceGrey, RoundedCornerShape(24.dp))
                .padding(24.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Cashout via UPI",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = upiId,
                    onValueChange = { upiId = it },
                    placeholder = { Text("Enter UPI ID (e.g. user@okhdfc)", color = White80) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = FireOrange,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) { Text("Cancel", color = White80) }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { onConfirm(upiId) },
                        colors = ButtonDefaults.buttonColors(containerColor = FireOrange),
                        enabled = upiId.contains("@")
                    ) {
                        Text("Withdraw")
                    }
                }
            }
        }
    }
}

@Composable
fun CouponCard(coupon: CouponResponse, onRedeem: () -> Unit) {
    // ... existing implementation
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceGrey)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Placeholder Logo
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(if(coupon.partner.contains("UPI")) Color.White else Color.White), // Could use different color
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if(coupon.partner.contains("UPI")) "₹" else coupon.partner.take(1),
                color = if(coupon.partner.contains("UPI")) FireOrange else PremiumBlack,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(coupon.partner, color = Color.White, fontWeight = FontWeight.Bold)
            Text(coupon.description, color = White80, style = Typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text("${coupon.points_cost} pts", color = RewardGold, fontWeight = FontWeight.Bold)
        }

        Button(
            onClick = onRedeem,
            colors = ButtonDefaults.buttonColors(containerColor = if(coupon.partner.contains("UPI")) RewardGold else FireOrange),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(if(coupon.partner.contains("UPI")) "Withdraw" else "Get", color = if(coupon.partner.contains("UPI")) PremiumBlack else Color.White)
        }
    }
}

@Composable
fun RedemptionDialog(
    result: com.reelrewards.app.ui.viewmodel.RedemptionResult,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceGrey, RoundedCornerShape(24.dp))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                when (result) {
                    is com.reelrewards.app.ui.viewmodel.RedemptionResult.Success -> {
                        val isCash = result.code == "TXN_PENDING"
                        Text(if(isCash) "Processing Payout" else "🎉 Redemption Successful!", color = RewardGold, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))
                        if(isCash) {
                             Text("Your payout of the selected amount has been initiated via UPI.", color = White80,  textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                             Spacer(modifier = Modifier.height(8.dp))
                             Text("Funds typically arrive in 5-10 minutes.", color = White80.copy(alpha=0.5f), fontSize = 12.sp)
                        } else {
                            Text("Your Coupon Code:", color = White80)
                            Text(
                                result.code,
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier
                                    .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    }
                    is com.reelrewards.app.ui.viewmodel.RedemptionResult.Error -> {
                        Text("Oops!", color = Color.Red, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(result.message, color = White80)
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = FireOrange)) {
                    Text("Close")
                }
            }
        }
    }
}
