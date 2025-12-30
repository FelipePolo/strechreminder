package com.fpstudio.stretchreminder.util

import com.fpstudio.stretchreminder.BuildConfig

/**
 * Utility to check if subscription bypass is enabled.
 * This is ONLY enabled in debug builds to allow development without Google Play Billing.
 * 
 * In release builds, this will always return false and real billing will be required.
 */
object DebugBillingBypass {
    
    /**
     * Check if subscription bypass is enabled.
     * Returns true ONLY in debug builds.
     */
    fun isEnabled(): Boolean {
        return BuildConfig.BYPASS_SUBSCRIPTION
    }
    
    /**
     * Simulate having an active subscription in debug mode.
     * This allows testing premium features without actual billing.
     */
    fun hasActiveSubscription(): Boolean {
        return isEnabled() // In debug, always return true
    }
    
    /**
     * Log bypass status for debugging
     */
    fun logBypassStatus() {
        if (isEnabled()) {
            android.util.Log.d("DebugBillingBypass", "⚠️ SUBSCRIPTION BYPASS ENABLED - Debug build only")
        }
    }
}
