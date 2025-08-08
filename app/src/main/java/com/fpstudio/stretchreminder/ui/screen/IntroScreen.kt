package com.fpstudio.stretchreminder.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.constraintlayout.compose.ConstraintLayout
import com.fpstudio.stretchreminder.ui.component.VideoPlayer


@Composable
fun IntroScreen() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current
        val rawUri = "android.resource://${context.packageName}/raw/onboarding"
        VideoPlayer(videoSource = rawUri, modifier = Modifier.fillMaxSize(), showControls = false)
    }
}