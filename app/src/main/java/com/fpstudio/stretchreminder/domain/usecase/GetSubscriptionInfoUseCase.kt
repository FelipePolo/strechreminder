package com.fpstudio.stretchreminder.domain.usecase

import com.fpstudio.stretchreminder.domain.model.SubscriptionInfo
import com.fpstudio.stretchreminder.domain.repository.BillingRepository

class GetSubscriptionInfoUseCase(
    private val billingRepository: BillingRepository
) {
    suspend operator fun invoke(): SubscriptionInfo? {
        return billingRepository.getSubscriptionInfo()
    }
}
