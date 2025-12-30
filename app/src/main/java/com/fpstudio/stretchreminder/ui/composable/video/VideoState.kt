package com.fpstudio.stretchreminder.ui.composable.video

import android.content.Context
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.cache.Cache
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Stable
@UnstableApi
class VideoState(
    val useController: Boolean = false,
    val playWhenReady: Boolean = true,
    val repeatMode: Int = Player.REPEAT_MODE_OFF,
    val resizeMode: Int = AspectRatioFrameLayout.RESIZE_MODE_ZOOM,
    val context: Context,
    val coroutineScope: CoroutineScope,
    val cache: Cache? = null
) : Player.Listener {

    private var currentPlaying: Boolean = false

    private var onReadyListener: (VideoState) -> Unit = {}
    private var onVideoEndListener: (VideoState) -> Unit = {}

    val exo: ExoPlayer = createPreparedExoPlayer(context, cache).also {
        it.repeatMode = repeatMode
        currentPlaying = playWhenReady
        it.playWhenReady = playWhenReady
        it.addListener(this)
    }

    fun getRemainingTime(listener: (Long) -> Unit) {
        coroutineScope.launch {
            while (true) {
                val remaining = (exo.duration - exo.currentPosition).coerceAtLeast(0L)
                listener(remaining)
                delay(300)
            }
        }
    }

    fun onVideoEnd(listener: (VideoState) -> Unit) {
        onVideoEndListener = listener
    }

    fun onReady(listener: (VideoState) -> Unit) {
        onReadyListener = listener
    }

    fun play() {
        exo.play()
        currentPlaying = true
    }

    fun isPlaying(): Boolean {
        return exo.isPlaying
    }

    fun isNotPlaying(): Boolean {
        return !exo.isPlaying
    }

    fun shouldPlay(): Boolean {
        return currentPlaying && !exo.isPlaying
    }

    fun pause() {
        exo.pause()
        currentPlaying = false
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

    fun loadVideo(videoSource: String) {
        exo.setMediaItem(createMediaSource(videoSource))
        exo.prepare()
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        if (playbackState == Player.STATE_READY) {
            if (playWhenReady) {
                play()
            }
            onReadyListener(this)
        }
        if (playbackState == Player.STATE_ENDED) {
            onVideoEndListener(this)
        }
    }
}

private fun createMediaSource(videoSource: String): MediaItem {
    return MediaItem.Builder()
        .setUri(videoSource.toUri())
        .build()
}

@OptIn(UnstableApi::class)
private fun createPreparedExoPlayer(context: Context, cache: Cache?): ExoPlayer {
    val exoPlayerBuilder = ExoPlayer.Builder(context)
    
    // If cache is provided, configure ExoPlayer to use it
    if (cache != null) {
        // Use DefaultDataSource.Factory to support both HTTP and local resources
        val upstreamFactory = androidx.media3.datasource.DefaultDataSource.Factory(
            context,
            androidx.media3.datasource.DefaultHttpDataSource.Factory()
                .setUserAgent("StretchReminder")
        )
        
        val cacheDataSourceFactory = androidx.media3.datasource.cache.CacheDataSource.Factory()
            .setCache(cache)
            .setUpstreamDataSourceFactory(upstreamFactory)
            .setCacheWriteDataSinkFactory(null) // Use default
            .setFlags(androidx.media3.datasource.cache.CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
        
        exoPlayerBuilder.setMediaSourceFactory(
            androidx.media3.exoplayer.source.DefaultMediaSourceFactory(context)
                .setDataSourceFactory(cacheDataSourceFactory)
        )
    }
    
    return exoPlayerBuilder.build()
}

@Composable
@OptIn(UnstableApi::class)

fun rememberVideoState(
    useController: Boolean = false,
    playWhenReady: Boolean = false,
    repeatMode: Int = Player.REPEAT_MODE_OFF,
    resizeMode: Int = AspectRatioFrameLayout.RESIZE_MODE_ZOOM,
    cache: Cache? = org.koin.compose.koinInject()
): VideoState {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    return remember(cache) {
        VideoState(
            useController = useController,
            playWhenReady = playWhenReady,
            repeatMode = repeatMode,
            resizeMode = resizeMode,
            context = context,
            coroutineScope = coroutineScope,
            cache = cache
        )
    }
}
