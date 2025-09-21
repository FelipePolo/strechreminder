package com.fpstudio.stretchreminder.foundation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MviDelegate<UiState, Intent, SideEffect> internal constructor(
    initialState: UiState,
) : Mvi<UiState, Intent, SideEffect> {

    private val _uiState = MutableStateFlow(initialState)
    override val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _sideEffect by lazy { Channel<SideEffect>() }
    override val sideEffect: Flow<SideEffect> by lazy { _sideEffect.receiveAsFlow() }

    override fun handleIntent(intent: Intent) {
        // Do nothing by default
    }

    override fun updateUiState(newUiState: UiState) {
        _uiState.update { newUiState }
    }

    override fun updateUiState(reducer: UiState.() -> UiState) {
        _uiState.update(reducer)
    }

    override fun CoroutineScope.emitSideEffect(sideEffect: SideEffect) {
        this.launch { _sideEffect.send(sideEffect) }
    }

}