package com.fpstudio.stretchreminder.ui.composable.video

import android.view.View
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
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
                useController = state.useController
                resizeMode = state.resizeMode
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
                val prev = findViewById<View>(
                    context.resources.getIdentifier("exo_prev", "id", context.packageName)
                )
                prev?.visibility = View.GONE

                val next = findViewById<View>(
                    context.resources.getIdentifier("exo_next", "id", context.packageName)
                )
                next?.visibility = View.GONE

                controllerShowTimeoutMs = 1000
                controllerAutoShow = false
            }
        }
    )

    OnAppLifecycle(
        onBackground = {
            if (state.isPlaying()) {
                state.pause()
            }
        },
        onForeground = {
            if (state.shouldPlay()) {
                state.play()
            }
        }
    )
}


@Preview
@Composable
@OptIn(UnstableApi::class)
fun VideoPreview() {
    val state = rememberVideoState(
        repeatMode = Player.REPEAT_MODE_OFF,
        playWhenReady = true,
        useController = false
    )
    state.loadVideo(videoSource = "https://v1.pinimg.com/videos/mc/720p/57/1b/8f/571b8fe9511642186296640552970415.mp4")
    Video(
        modifier = Modifier,
        state = state
    )
}