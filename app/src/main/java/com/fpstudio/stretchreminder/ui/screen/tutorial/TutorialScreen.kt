package com.fpstudio.stretchreminder.ui.screen.tutorial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fpstudio.stretchreminder.ui.composable.button.StretchButton
import com.fpstudio.stretchreminder.ui.screen.congratulation.CongratulationScreen
import org.koin.androidx.compose.koinViewModel
import com.fpstudio.stretchreminder.ui.screen.tutorial.TutorialScreenContract.Intent
import com.fpstudio.stretchreminder.ui.screen.tutorial.TutorialScreenContract.UiState
import com.fpstudio.stretchreminder.ui.theme.Gray2
import com.fpstudio.stretchreminder.ui.theme.Gray3

@Composable
fun TutorialScreen(
    viewModel: TutorialViewModel = koinViewModel(),
    onNavigateNext: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    TutorialContent(
        uiState = uiState.value,
        onIntent = viewModel::handleIntent
    )
}

@Composable
fun TutorialContent(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onIntent: (Intent) -> Unit
) {
    val pagerState = rememberPagerState {
        uiState.tutorialScreens.size
    }

    HorizontalPager(
        state = pagerState
    ) { page ->
        val screen = uiState.tutorialScreens[page]
        when (screen) {
            is TutorialUiModel.Welcome -> {
                WelcomeScreen(
                    state = screen,
                    onIntent = onIntent
                )
            }

            is TutorialUiModel.Tutorial -> {

            }

            is TutorialUiModel.Complete -> {

            }
        }
    }
}

@Composable
private fun WelcomeScreen(
    state: TutorialUiModel.Welcome,
    onIntent: (Intent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val composition by rememberLottieComposition(
            RawRes(state.icon)
        )

        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )

        LottieAnimation(
            composition = composition,
            contentScale = ContentScale.FillBounds,
            progress = { progress }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = state.title,
            color = Gray2,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = state.description,
            color = Gray3,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(24.dp))

        StretchButton(state = state.button) {
            onIntent(Intent.StartTutorial)
        }

        val pagerState = rememberPagerState { 5 }
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
        ) {

        }
    }
}

@Composable
private fun TutorialScreen(state: TutorialUiModel.Tutorial) {
    
}

@Composable
private fun CompleteScreen(state: TutorialUiModel.Complete) {
    CongratulationScreen(
        uiModel = state.congrats,
    ) {

    }
}


