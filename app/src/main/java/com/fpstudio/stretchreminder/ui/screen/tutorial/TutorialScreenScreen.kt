package com.fpstudio.stretchreminder.ui.screen.tutorial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fpstudio.stretchreminder.util.foundation.LaunchedSideEffect
import com.fpstudio.stretchreminder.ui.composable.button.StretchButton
import com.fpstudio.stretchreminder.ui.screen.exercise.ExerciseScreen
import org.koin.androidx.compose.koinViewModel
import com.fpstudio.stretchreminder.ui.screen.tutorial.TutorialScreenContract.Intent
import com.fpstudio.stretchreminder.ui.screen.tutorial.TutorialScreenContract.UiState
import com.fpstudio.stretchreminder.ui.screen.tutorial.TutorialScreenContract.SideEffect
import com.fpstudio.stretchreminder.ui.theme.Gray3

@Composable
fun TutorialScreen(
    viewModel: TutorialScreenViewModel = koinViewModel(),
    onNavigateNext: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    TutorialScreenContent(
        uiState = uiState.value,
        onIntent = viewModel::handleIntent
    )

    LaunchedSideEffect(viewModel.sideEffect) {
        when (it) {
            is SideEffect.NavigateNext -> {
                onNavigateNext()
            }
        }
    }
}

@Composable
fun TutorialScreenContent(
    uiState: UiState,
    onIntent: (Intent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState {
        uiState.exerciseRoutineScreens.size
    }

    LaunchedEffect(uiState.page) {
        pagerState.animateScrollToPage(uiState.page)
    }

    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        userScrollEnabled = false
    ) { page ->
        val screen = uiState.exerciseRoutineScreens[page]
        when (screen) {
            is TutorialScreenUiModel.Welcome -> {
                WelcomeScreen(
                    state = screen,
                    onIntent = onIntent
                )
            }

            is TutorialScreenUiModel.TutorialScreen -> {
                TutorialScreenPlayer(
                    state = screen,
                    onIntent = onIntent
                )
            }
        }
    }
}

@Composable
fun WelcomeScreen(
    state: TutorialScreenUiModel.Welcome,
    onIntent: (Intent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
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
            modifier = Modifier.size(80.dp),
            composition = composition,
            contentScale = ContentScale.FillBounds,
            progress = { progress }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = state.title,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = state.description,
            color = Gray3,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(24.dp))

        StretchButton(state = state.button) {
            onIntent(Intent.StartExerciseRoutine)
        }
    }
}

@Composable
private fun TutorialScreenPlayer(state: TutorialScreenUiModel.TutorialScreen, onIntent: (Intent) -> Unit) {
    ExerciseScreen(
        state = state.exerciseScreenState
    ) {
        // ExerciseScreen handles congratulations and navigation
        onIntent(Intent.FinishExerciseRoutine)
    }
}
