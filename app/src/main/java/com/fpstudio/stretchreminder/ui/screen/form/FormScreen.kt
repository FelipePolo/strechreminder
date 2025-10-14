package com.fpstudio.stretchreminder.ui.screen.form

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fpstudio.stretchreminder.foundation.LaunchedSideEffect
import com.fpstudio.stretchreminder.ui.component.form.FormComponent
import com.fpstudio.stretchreminder.ui.composable.button.StretchButton
import com.fpstudio.stretchreminder.ui.composable.permision.notification.askPermission
import com.fpstudio.stretchreminder.ui.composable.permision.notification.createNotificationPermissionLauncher
import com.fpstudio.stretchreminder.ui.composable.questionProgressBar.QuestionProgressBar
import com.fpstudio.stretchreminder.ui.composable.transitions.smooth.SmoothOverlay
import com.fpstudio.stretchreminder.ui.composable.transitions.splash.LiquidSplashOverlay
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.koinViewModel
import com.fpstudio.stretchreminder.ui.screen.form.FormScreenContract.Intent
import com.fpstudio.stretchreminder.ui.screen.form.FormScreenContract.SideEffect.RequestNotificationPermission
import com.fpstudio.stretchreminder.ui.screen.form.FormScreenContract.SideEffect.NavigateNext
import com.fpstudio.stretchreminder.ui.screen.promises.madeforyou.MadeForYouScreen
import com.fpstudio.stretchreminder.ui.screen.promises.plansuccess.PlanSuccessScreen

@Composable
fun FormScreen(
    viewmodel: FormViewModel = koinViewModel(),
    onNavigate: () -> Unit
) {
    val uiState = viewmodel.uiState.collectAsStateWithLifecycle()

    FormScreenContent(
        uiState = uiState.value,
        onIntent = viewmodel::handleIntent,
        sideEffect = viewmodel.sideEffect,
        onNavigate = onNavigate,
        modifier = Modifier
    )
}

@Composable
fun FormScreenContent(
    uiState: FormScreenContract.UiState,
    onIntent: (Intent) -> Unit,
    sideEffect: Flow<FormScreenContract.SideEffect>,
    onNavigate: () -> Unit,
    modifier: Modifier
) {
    val notificationPermission = createNotificationPermissionLauncher(
        oGranted = {
            onIntent(Intent.OnNotificationAllow)
        },
        onDenied = {
            onIntent(Intent.OnNotificationDeny)
        }
    )

    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Box(modifier = modifier.fillMaxSize()) {
            ConstraintLayout(
                modifier = modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                val pagerState = rememberPagerState(initialPage = uiState.page) {
                    uiState.form.size
                }
                val (questionProgressBar, pager, nextButton) = createRefs()

                LaunchedEffect(uiState.page) {
                    pagerState.animateScrollToPage(uiState.page)
                }

                QuestionProgressBar(
                    visibility = uiState.shouldShowQuestionProgressBar,
                    currentQuestion = uiState.page + 1,
                    totalQuestions = uiState.form.size,
                    backButton = uiState.backButton,
                    modifier = modifier
                        .fillMaxWidth()
                        .constrainAs(questionProgressBar) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    onIntent(Intent.OnBackClick)
                }

                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = false,
                    modifier = modifier
                        .constrainAs(pager) {
                            top.linkTo(questionProgressBar.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .fillMaxSize()
                ) { page ->
                    FormComponent(
                        questions = uiState.form[page].questions,
                        onError = sideEffect,
                        onSelect = { index, answer ->
                            onIntent(Intent.OnQuestionAnswered(index, answer))
                        }
                    )
                }

                StretchButton(
                    state = uiState.nextButton,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .constrainAs(nextButton) {
                            bottom.linkTo(parent.bottom, 32.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(horizontal = 16.dp),
                    onClick = {
                        onIntent(Intent.OnContinueClick)
                    }
                )
            }

            SmoothOverlay(
                visible = uiState.madeForYou.isVisible,
            ) {
                MadeForYouScreen(
                    onBackClick = {
                        onIntent(Intent.OnMadeForYouCloseClick)
                    },
                    onContinueClick = {
                        onIntent(Intent.OnMadeForYouCloseClick)
                    }
                )
            }

            LiquidSplashOverlay(
                visible = uiState.planSuccess.isVisible,
            ) {
                PlanSuccessScreen(
                    onBackClick = {
                        onIntent(Intent.OnPlanSuccessCloseClick)
                    },
                    onContinueClick = {
                        onIntent(Intent.OnPlanSuccessCloseClick)
                    }
                )
            }

            LaunchedSideEffect(sideEffect) { effect ->
                if (effect is RequestNotificationPermission) {
                    notificationPermission.askPermission {
                        onIntent(Intent.OnNotificationAllow)
                    }
                }
                if (effect is NavigateNext) {
                    onNavigate()
                }
            }
        }
    }
}
