package com.reelrewards.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reelrewards.app.ui.theme.*

@Composable
fun LoginScreen(
    onLoginClick: (String) -> Unit,
    onGoogleLoginClick: () -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    var phoneNumber by remember { mutableStateOf("") }
    var referralCode by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
        .fillMaxSize()
        .background(PremiumBlack)
    ) {
        // --- BACKGROUND ACCENT (Subtle Glow) ---
        Box(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.TopEnd)
                .offset(x = 100.dp, y = (-100).dp)
                .background(Brush.radialGradient(listOf(FireOrange.copy(alpha = 0.2f), Color.Transparent)))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // --- LOGO/TITLE ---
            Text(
                text = "ReelRewards",
                style = Typography.displayLarge,
                color = FireOrange,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "Watch. Earn. Duel.",
                style = Typography.bodyLarge,
                color = White80,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // --- WELCOME TEXT ---
            Text(
                text = "Enter your phone number to start earning",
                style = Typography.titleLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- PHONE INPUT (Glassmorphism Style) ---
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { if (it.length <= 10) phoneNumber = it },
                placeholder = { Text("Phone Number", color = White80) },
                prefix = { Text("+91 ", color = Color.White, fontWeight = FontWeight.Bold) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceGrey.copy(alpha = 0.5f)),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = FireOrange,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- REFERRAL CODE (Optional) ---
            OutlinedTextField(
                value = referralCode,
                onValueChange = { referralCode = it },
                placeholder = { Text("Referral Code (Optional)", color = White80) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceGrey.copy(alpha = 0.5f)),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = RewardGold,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                enabled = !isLoading
            )
            
            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(errorMessage, color = Color.Red, style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- LOGIN BUTTON ---
            Button(
                onClick = { onLoginClick(phoneNumber) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(),
                enabled = !isLoading && phoneNumber.length == 10
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(if (isLoading) SolidColor(Color.Gray) else Brush.linearGradient(FireGradient)),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text(
                            "Send OTP",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- GOOGLE SIGN IN ---
            TextButton(
                onClick = onGoogleLoginClick,
                enabled = !isLoading
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Or continue with ",
                        color = White80,
                        style = Typography.bodyLarge
                    )
                    Text(
                        "Google",
                        color = Color.White,
                        style = Typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // --- FOOTER T&C ---
        Text(
            text = "By continuing, you agree to our Terms & Privacy Policy.",
            color = White80.copy(alpha = 0.5f),
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            textAlign = TextAlign.Center
        )
    }
}
