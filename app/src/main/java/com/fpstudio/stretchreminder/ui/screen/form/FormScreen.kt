package com.fpstudio.stretchreminder.ui.screen.form

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fpstudio.stretchreminder.ui.component.form.FormComponent
import com.fpstudio.stretchreminder.ui.composable.button.StretchButton
import com.fpstudio.stretchreminder.ui.composable.congratulation.Congratulation
import com.fpstudio.stretchreminder.ui.composable.questionProgressBar.QuestionProgressBar
import org.koin.androidx.compose.koinViewModel


@Composable
fun FormScreen(viewmodel: FormViewModel = koinViewModel()) {
    val uiState = viewmodel.uiState.collectAsStateWithLifecycle()

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewmodel.onEvent(FormEvent.OnNotificationAllow)
        } else {
            viewmodel.onEvent(FormEvent.OnNotificationDeny)
        }
    }

    Scaffold { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            val pagerState =
                rememberPagerState(initialPage = uiState.value.page) { uiState.value.form.size }
            val (questionProgressBar, pager, button) = createRefs()

            LaunchedEffect(uiState.value.page) {
                pagerState.animateScrollToPage(uiState.value.page)
            }

            QuestionProgressBar(
                visibility = uiState.value.shouldShowQuestionProgressBar,
                currentQuestion = uiState.value.page + 1,
                totalQuestions = uiState.value.form.size,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(questionProgressBar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
                modifier = Modifier
                    .constrainAs(pager) {
                        top.linkTo(questionProgressBar.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }.fillMaxSize()
            ) { page ->
                if (uiState.value.congratulation.visible) {
                    Congratulation(
                        uiModel = uiState.value.congratulation
                    )
                }else {
                    FormComponent(
                        questions = uiState.value.form[page].questions,
                        onSelect = { index, answer ->
                            viewmodel.onEvent(FormEvent.OnSelection(index, answer))
                        }
                    )
                }
            }

            StretchButton(
                state = uiState.value.button,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom, 32.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(horizontal = 16.dp),
                onClick = {
                    viewmodel.onEvent(FormEvent.OnContinueClick)
                }
            )
        }
    }

    LaunchedEffect(uiState.value.shouldRequestNotificationPermission) {
        if (uiState.value.shouldRequestNotificationPermission) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                viewmodel.onEvent(FormEvent.OnNotificationAllow)
            }
        }
    }
}
