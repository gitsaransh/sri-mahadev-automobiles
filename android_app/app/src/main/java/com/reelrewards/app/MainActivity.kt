package com.reelrewards.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.reelrewards.app.ui.nav.AppNavGraph
import com.reelrewards.app.ui.theme.ReelsRewardTheme
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.gms.ads.MobileAds

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize Mobile Ads SDK
        MobileAds.initialize(this) {}
        
        setContent {
            ReelsRewardTheme {
                AppNavGraph()
            }
        }
    }
}
