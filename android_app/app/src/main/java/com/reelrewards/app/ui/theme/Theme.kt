package com.reelrewards.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = FireOrange,
    secondary = RewardGold,
    tertiary = FireRed,
    background = PremiumBlack,
    surface = SurfaceGrey,
    onPrimary = Color.White,
    onSecondary = PremiumBlack,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun ReelsRewardTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme // Forced dark mode for premium feel

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // We will define this next
        content = content
    )
}
