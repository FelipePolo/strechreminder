package com.fpstudio.stretchreminder.foundation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Composable
fun <SideEffect> LaunchedSideEffect(
    sideEffect: Flow<SideEffect>,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    coroutineContext: CoroutineContext = Dispatchers.Main.immediate,
    onSideEffect: suspend CoroutineScope.(SideEffect) -> Unit,
) {
    LaunchedEffect(sideEffect, lifecycleOwner.lifecycle) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            if (coroutineContext == EmptyCoroutineContext) {
                sideEffect.collect { onSideEffect(it) }
            } else {
                withContext(coroutineContext) {
                    sideEffect.collect { onSideEffect(it) }
                }
            }
        }
    }
}
