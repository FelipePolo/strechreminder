package com.fpstudio.stretchreminder.domain.usecase

import com.fpstudio.stretchreminder.util.NetworkConnectivityHelper

class CheckNetworkConnectivityUseCase(
    private val networkConnectivityHelper: NetworkConnectivityHelper
) {
    /**
     * Checks if the device has an active internet connection.
     * @return true if connected to internet, false otherwise
     */
    operator fun invoke(): Boolean {
        return networkConnectivityHelper.isNetworkAvailable()
    }
}
