package com.fpstudio.stretchreminder.ui.composable.video

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Stable
class VideoState(
    val videoSource: String,
    val playWhenReady: Boolean = true,
    val repeatMode: Int = Player.REPEAT_MODE_ONE,
    val context: Context,
    val coroutineScope: CoroutineScope
): Player.Listener {

    val exo: ExoPlayer = createPreparedExoPlayer(
        context, videoSource
    ).also {
        it.repeatMode = repeatMode
        it.playWhenReady = playWhenReady
        it.addListener(this)
    }

    fun progressListener(listener: (Long) -> Unit) {
        coroutineScope.launch {
            while (true) {
                listener(exo.currentPosition)
                delay(300)
            }
        }
    }

    fun play() {
        exo.play()
    }

    fun pause() {
        exo.pause()
    }

    fun prepare() {
        exo.prepare()
    }

    fun replay() {
        exo.seekTo(0)
        exo.play()
    }

    fun seekToStart() {
        exo.seekTo(0)
    }

    fun seekToEnd() {
        exo.seekTo(exo.duration)
    }

    fun seekTo(seconds: Long) {
        exo.seekTo(seconds)
    }

    fun release() {
        exo.release()
    }
}

private fun createMediaSource(videoSource: String): MediaItem {
    return MediaItem.Builder()
        .setUri(videoSource.toUri())
        .build()
}

private fun createPreparedExoPlayer(context: Context, videoSource: String): ExoPlayer {
    val exoPlayer = ExoPlayer.Builder(context).build()
    val mediaItem = createMediaSource(videoSource)
    exoPlayer.setMediaItem(mediaItem)
    return exoPlayer
}

@Composable
fun rememberVideoState(
    videoSource: String,
    playWhenReady: Boolean = true,
    repeatMode: Int = Player.REPEAT_MODE_ONE,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): VideoState {
    val context = LocalContext.current
    return remember {
        VideoState(
            videoSource,
            playWhenReady,
            repeatMode,
            context,
            coroutineScope
        )
    }
}
