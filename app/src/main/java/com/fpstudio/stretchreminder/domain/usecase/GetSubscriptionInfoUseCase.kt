package com.fpstudio.stretchreminder.domain.usecase

import com.fpstudio.stretchreminder.domain.model.PlanType
import com.fpstudio.stretchreminder.domain.model.SubscriptionInfo
import com.fpstudio.stretchreminder.domain.repository.RevenueCatRepository

class GetSubscriptionInfoUseCase(
    private val revenueCatRepository: RevenueCatRepository
) {
    suspend operator fun invoke(): SubscriptionInfo? {
        return revenueCatRepository.getCustomerInfo().getOrNull()?.let { customerInfo ->
            val premiumEntitlement = customerInfo.entitlements["strechreminder"]
            
            if (premiumEntitlement?.isActive == true) {
                // Map product identifier to our PlanType
                val planType = when {
                    premiumEntitlement.productIdentifier.contains("yearly", ignoreCase = true) -> PlanType.ANNUAL
                    premiumEntitlement.productIdentifier.contains("monthly", ignoreCase = true) -> PlanType.MONTHLY
                    else -> PlanType.NONE
                }
                
                val renewalDate = premiumEntitlement.expirationDate?.time ?: System.currentTimeMillis()
                
                SubscriptionInfo(
                    isActive = true,
                    planType = planType,
                    renewalDate = renewalDate
                )
            } else {
                null
            }
        }
    }
}
