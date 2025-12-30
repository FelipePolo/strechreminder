package com.fpstudio.stretchreminder.domain.usecase

import com.android.billingclient.api.ProductDetails
import com.fpstudio.stretchreminder.domain.repository.BillingRepository
import com.fpstudio.stretchreminder.domain.repository.BillingResult

class PurchaseSubscriptionUseCase(
    private val billingRepository: BillingRepository
) {
    suspend operator fun invoke(
        activity: android.app.Activity,
        productDetails: ProductDetails
    ): BillingResult {
        return billingRepository.launchPurchaseFlow(activity, productDetails)
    }
}
