package com.fpstudio.stretchreminder.domain.usecase

import com.fpstudio.stretchreminder.domain.repository.RevenueCatRepository

class CheckEntitlementUseCase(
    private val revenueCatRepository: RevenueCatRepository
) {
    operator fun invoke(entitlementId: String = "strechreminder"): Boolean {
        return revenueCatRepository.hasActiveEntitlement(entitlementId)
    }
}
