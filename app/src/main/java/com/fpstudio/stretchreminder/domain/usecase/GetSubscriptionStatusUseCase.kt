package com.fpstudio.stretchreminder.domain.usecase

import com.fpstudio.stretchreminder.domain.repository.BillingRepository

class GetSubscriptionStatusUseCase(
    private val billingRepository: BillingRepository
) {
    suspend operator fun invoke(): Boolean {
        return billingRepository.hasActiveSubscription()
    }
}
