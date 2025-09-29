package com.fpstudio.stretchreminder.ui.screen.threeyes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fpstudio.stretchreminder.ui.screen.promises.agreement.AgreementScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.koin.androidx.compose.koinViewModel


@Composable
fun ThreeYesScreen(
    viewmodel: ThreeYesViewModel = koinViewModel(),
    onNavigateToHome: () -> Unit
) {
    val uiState = viewmodel.uiState.collectAsStateWithLifecycle()

    ThreeYesContent(
        state = uiState.value,
        onIntent = viewmodel::handleIntent,
        onSideEffect = viewmodel.sideEffect
    )
}

@Composable
fun ThreeYesContent(
    state: ThreeYesContract.UiState,
    onIntent: (ThreeYesContract.Intent) -> Unit,
    onSideEffect: Flow<ThreeYesContract.SideEffect>
) {
    val pagerState = rememberPagerState {
        state.agreementScreens.size
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        userScrollEnabled = false,
    ) { page ->
        AgreementScreen(
            state = state.agreementScreens[page],
            onNoClick = { onIntent(ThreeYesContract.Intent.OnContinue) },
            onYesClick = { onIntent(ThreeYesContract.Intent.OnContinue) }
        )
    }
}

// Implementent Preview
@Preview
@Composable
fun ThreeYesScreenPreview() {
    ThreeYesContent(
        state = ThreeYesContract.UiState(),
        onIntent = {},
        onSideEffect = emptyFlow()
    )
}