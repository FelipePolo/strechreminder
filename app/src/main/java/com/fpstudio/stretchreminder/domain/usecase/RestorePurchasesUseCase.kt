package com.fpstudio.stretchreminder.domain.usecase

import com.fpstudio.stretchreminder.domain.repository.BillingRepository
import com.fpstudio.stretchreminder.domain.repository.BillingResult

class RestorePurchasesUseCase(
    private val billingRepository: BillingRepository
) {
    suspend operator fun invoke(): BillingResult {
        return billingRepository.restorePurchases()
    }
}
