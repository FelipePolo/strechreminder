package com.fpstudio.stretchreminder.util.foundation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface Mvi<UiState, Intent, SideEffect> {
    val uiState: StateFlow<UiState>

    val sideEffect: Flow<SideEffect>

    fun handleIntent(intent: Intent)

    fun updateUiState(newUiState: UiState)

    fun updateUiState(reducer: UiState.() -> UiState)

    fun CoroutineScope.emitSideEffect(sideEffect: SideEffect)

}

@Stable
@Composable
fun <UiState, Intent, SideEffect> Mvi<UiState, Intent, SideEffect>.unPack() =
    Triple(uiState, sideEffect, ::handleIntent)