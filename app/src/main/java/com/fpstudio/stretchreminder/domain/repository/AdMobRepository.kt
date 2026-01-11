package com.fpstudio.stretchreminder.domain.repository

import android.app.Activity
import android.content.Context

interface AdMobRepository {
    /**
     * Initialize MobileAds SDK
     */
    fun initialize(context: Context)

    /**
     * Load a rewarded ad
     */
    fun loadRewardedAd(context: Context, adUnitId: String)

    /**
     * Show a rewarded ad if loaded.
     * @param activity The host activity
     * @param onRewardEarned Callback when user earns a reward
     * @param onAdClosed Callback when ad is closed (regardless of reward)
     * @param onAdFailedToLoad Callback if ad is not ready
     */
    fun showRewardedAd(
        activity: Activity, 
        onRewardEarned: () -> Unit,
        onAdClosed: () -> Unit = {},
        onAdFailedToLoad: () -> Unit = {}
    )
    
    /**
     * Check if ad is currently loaded and ready
     */
    fun isAdReady(): Boolean
}
