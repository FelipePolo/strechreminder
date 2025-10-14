package com.fpstudio.stretchreminder.ui.composable.video

import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.fpstudio.stretchreminder.ui.composable.lifecycle.OnAppLifecycle

@OptIn(UnstableApi::class)
@Composable
fun Video(
    modifier: Modifier = Modifier,
    state: VideoState
) {
    DisposableEffect(state) {
        state.prepare()
        onDispose {
            state.release()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            PlayerView(context).apply {
                player = state.exo
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
            }
        }
    )

    OnAppLifecycle(
        onBackground = {
            state.pause()
        },
        onForeground = {
            state.play()
        }
    )
}


@Preview
@Composable
fun VideoPreview() {
    val state = rememberVideoState(
        videoSource = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4",
        repeatMode = Player.REPEAT_MODE_ONE
    )
    Video(
        modifier = Modifier,
        state = state
    )
}