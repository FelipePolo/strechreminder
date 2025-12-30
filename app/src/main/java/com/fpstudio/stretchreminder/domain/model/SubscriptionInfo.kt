package com.fpstudio.stretchreminder.domain.model

data class SubscriptionInfo(
    val isActive: Boolean,
    val planType: PlanType,
    val renewalDate: Long // timestamp in millis
)

enum class PlanType {
    ANNUAL,
    MONTHLY,
    NONE
}
