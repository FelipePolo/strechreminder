package com.fpstudio.stretchreminder.data.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import com.fpstudio.stretchreminder.domain.repository.AdMobRepository
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdMobRepositoryImpl : AdMobRepository {

    private var rewardedAd: RewardedAd? = null
    private var isAdLoading = false
    private val TAG = "AdMobRepository"

    override fun initialize(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            MobileAds.initialize(context) { initializationStatus ->
                Log.d(TAG, "AdMob initialized: $initializationStatus")
            }
        }
    }

    override fun loadRewardedAd(context: Context, adUnitId: String) {
        if (rewardedAd != null || isAdLoading) {
            Log.d(TAG, "Ad already loaded or loading")
            return
        }

        isAdLoading = true
        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(
            context,
            adUnitId,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, "Ad failed to load: ${adError.message}")
                    rewardedAd = null
                    isAdLoading = false
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d(TAG, "Ad loaded successfully")
                    rewardedAd = ad
                    rewardedAd?.setImmersiveMode(true)
                    isAdLoading = false
                }
            }
        )
    }

    override fun showRewardedAd(
        activity: Activity,
        onRewardEarned: () -> Unit,
        onAdClosed: () -> Unit,
        onAdFailedToLoad: () -> Unit
    ) {
        if (rewardedAd == null) {
            Log.d(TAG, "The ad wasn't ready yet.")
            onAdFailedToLoad()
            return
        }

        rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so we don't show the ad a second time.
                Log.d(TAG, "Ad dismissed fullscreen content.")
                rewardedAd = null
                onAdClosed()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                // Called when ad fails to show.
                Log.e(TAG, "Ad failed to show fullscreen content: ${adError.message}")
                rewardedAd = null
                onAdFailedToLoad()
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.")
            }
        }

        rewardedAd?.show(activity) { rewardItem ->
            // Handle the reward.
            val rewardAmount = rewardItem.amount
            val rewardType = rewardItem.type
            Log.d(TAG, "User earned the reward: $rewardAmount $rewardType")
            onRewardEarned()
        }
    }

    override fun isAdReady(): Boolean {
        return rewardedAd != null
    }
}
