package com.fpstudio.stretchreminder.ui.screen.threeyes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fpstudio.stretchreminder.util.foundation.LaunchedSideEffect
import com.fpstudio.stretchreminder.ui.screen.promises.agreement.AgreementScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun ThreeYesScreen(
    viewmodel: ThreeYesViewModel = koinViewModel(),
    onNavigateToHome: () -> Unit
) {
    val uiState = viewmodel.uiState.collectAsStateWithLifecycle()

    ThreeYesContent(
        state = uiState.value,
        onIntent = viewmodel::handleIntent
    )

    LaunchedSideEffect(viewmodel.sideEffect) { effect ->
        if (effect is ThreeYesContract.SideEffect.NavigateNext) {
            onNavigateToHome()
        }
    }
}

@Composable
fun ThreeYesContent(
    state: ThreeYesContract.UiState,
    onIntent: (ThreeYesContract.Intent) -> Unit
) {
    val pagerState = rememberPagerState {
        state.agreementScreens.size
    }

    LaunchedEffect(state.page) {
        pagerState.animateScrollToPage(state.page)
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

@Preview
@Composable
fun ThreeYesScreenPreview() {
    var state by remember { mutableStateOf(ThreeYesContract.UiState()) }

    ThreeYesContent(
        state = state,
        onIntent = {
            state = state.copy(
                page = state.page + 1
            )
        }
    )
}
