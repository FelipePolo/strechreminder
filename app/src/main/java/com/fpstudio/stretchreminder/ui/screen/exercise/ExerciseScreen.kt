package com.fpstudio.stretchreminder.ui.screen.exercise

import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import com.fpstudio.stretchreminder.util.foundation.LaunchedSideEffect
import com.fpstudio.stretchreminder.ui.composable.getTimeString
import com.fpstudio.stretchreminder.ui.composable.progress.ProgressBar
import com.fpstudio.stretchreminder.ui.composable.video.Video
import com.fpstudio.stretchreminder.ui.composable.video.rememberVideoState
import com.fpstudio.stretchreminder.ui.theme.Green_secondary
import kotlinx.coroutines.delay
import com.fpstudio.stretchreminder.ui.screen.exercise.contract.ExerciseScreenContract.UiState
import com.fpstudio.stretchreminder.ui.screen.exercise.contract.ExerciseScreenContract.SideEffect
import com.fpstudio.stretchreminder.ui.screen.exercise.contract.ExerciseScreenContract.Intent
import com.fpstudio.stretchreminder.ui.screen.exercise.contract.Playlist
import com.fpstudio.stretchreminder.ui.screen.exercise.contract.PreText
import com.fpstudio.stretchreminder.ui.theme.Gray3
import com.fpstudio.stretchreminder.ui.theme.Gray5
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ExerciseScreen(
    state: UiState,
    viewModel: ExerciseScreenViewModel = koinViewModel(
        parameters = {
            parametersOf(state)
        }
    ),
    onNavigate: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    ExerciseScreenContent(
        state = uiState.value,
        onIntent = viewModel::handleIntent,
        sideEffect = viewModel.sideEffect
    )

    LaunchedSideEffect(viewModel.sideEffect) {
        if (it is SideEffect.NavigateNext) {
            onNavigate()
        }
    }
}

@Composable
private fun ExerciseScreenContent(
    state: UiState,
    onIntent: (Intent) -> Unit,
    sideEffect: Flow<SideEffect>
) {
    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            ExerciseScreenHeader(state = state)
            Spacer(modifier = Modifier.height(24.dp))
            ExerciseScreenVideo(
                state = state,
                onIntent = onIntent,
                sideEffect = sideEffect,
                modifier = Modifier
                    .weight(1F)
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = state.disclaimer,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                lineHeight = 12.sp,
                color = Gray3,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

}

@Composable
private fun ExerciseScreenHeader(state: UiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("⏱️ ${state.playlist.remainingTime.getTimeString()}")
        Spacer(modifier = Modifier.height(8.dp))
        ProgressBar(
            total = state.playlist.videoDuration.toFloat(),
            progress = state.playlist.remainingTime.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(50))
                .background(Gray5)
                .height(20.dp)
        )
    }
}

@OptIn(UnstableApi::class)
@Composable
private fun ExerciseScreenVideo(
    state: UiState,
    onIntent: (Intent) -> Unit,
    sideEffect: Flow<SideEffect>,
    modifier: Modifier,
) {
    val context = LocalContext.current

    val videoState = rememberVideoState(
        useController = true,
        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
    )

    LaunchedEffect(state.playlist.playIndex) {
        val videoUrl = state.playlist.videos[state.playlist.playIndex]
        val videoUri = if (videoUrl.startsWith("http")) {
            videoUrl
        } else {
            "android.resource://${context.packageName}/raw/$videoUrl"
        }
        videoState.loadVideo(videoUri)
    }

    Box(modifier = modifier) {

        Video(
            state = videoState,
            modifier = Modifier.fillMaxSize()
        )

        PreVideoText(
            state = state.preText,
            modifier = Modifier.align(Alignment.Center)
        ) {
            onIntent(Intent.UserPrepared)
        }

        VideoVerticalGradient(
            modifier = Modifier.align(Alignment.TopCenter),
            colors = listOf(Color.White, Color.Transparent)
        )

        VideoVerticalGradient(
            modifier = Modifier.align(Alignment.BottomCenter),
            colors = listOf(Color.Transparent, Color.White)
        )
    }

    videoState.onReady {
        onIntent(Intent.ReadyToExercise(videoState.exo.duration))
        it.getRemainingTime {
            onIntent(Intent.UpdateRemainingTime(it))
        }
        it.onVideoEnd {
            onIntent(Intent.SeeNextVideo)
        }
    }

    LaunchedSideEffect(sideEffect) {
        when (it) {
            SideEffect.Play -> {
                videoState.play()
            }

            SideEffect.Pause -> {
                videoState.pause()
            }

            else -> Unit
        }
    }
}

@Composable
private fun PreVideoText(
    state: PreText,
    modifier: Modifier,
    onPreTextTimeOut: () -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        exit = fadeOut(),
        visible = state.isVisible
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Text(
                text = state.text,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = Green_secondary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(horizontal = 12.dp)
            )
        }
    }
    LaunchedEffect(Unit) {
        if (state.isVisible) {
            delay(state.secondsToShowPreText)
            onPreTextTimeOut()
        }
    }
}

@Composable
private fun VideoVerticalGradient(
    modifier: Modifier, colors: List<Color>
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(
                Brush.verticalGradient(
                    colors = colors
                )
            )

    )
}


@Preview(showBackground = true)
@Composable
private fun PreviewExerciseScreen() {

    ExerciseScreenContent(
        state = UiState(
            playlist = Playlist(
                videoDuration = 1000,
                remainingTime = 322,
                videos = listOf("tutorial"),
            ),
            preText = PreText(
                text = "Get Ready",
                secondsToShowPreText = 3000,
                showPreTextForEachVideo = false,
            ),
            disclaimer = "If your experience pain or discomfort while exercising, please stop immediately and consult your doctor or qualified healthcare professional before continuing."
        ), onIntent = {}, sideEffect = emptyFlow()
    )
}


