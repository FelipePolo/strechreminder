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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fpstudio.stretchreminder.foundation.LaunchedSideEffect
import com.fpstudio.stretchreminder.ui.component.form.FormComponent
import com.fpstudio.stretchreminder.ui.composable.button.StretchButton
import com.fpstudio.stretchreminder.ui.composable.permision.notification.AskNotificationPermission
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
        uiState = uiState,
        onIntent = viewmodel::handleIntent,
        sideEffect = viewmodel.sideEffect,
        modifier = Modifier
    )

    LaunchedSideEffect(viewmodel.sideEffect) { effect ->
        if (effect is NavigateNext) {
            onNavigate()
        }
    }
}

@Composable
fun FormScreenContent(
    uiState: State<FormScreenContract.UiState>,
    onIntent: (Intent) -> Unit,
    sideEffect: Flow<FormScreenContract.SideEffect>,
    modifier: Modifier
) {
    var askNotificationPermission by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Box(modifier = modifier.fillMaxSize()) {
            ConstraintLayout(
                modifier = modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                val pagerState = rememberPagerState(initialPage = uiState.value.page) {
                    uiState.value.form.size
                }
                val (questionProgressBar, pager, nextButton) = createRefs()

                LaunchedEffect(uiState.value.page) {
                    pagerState.animateScrollToPage(uiState.value.page)
                }

                QuestionProgressBar(
                    visibility = uiState.value.shouldShowQuestionProgressBar,
                    currentQuestion = uiState.value.page + 1,
                    totalQuestions = uiState.value.form.size,
                    backButton = uiState.value.backButton,
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
                        questions = uiState.value.form[page].questions,
                        sideEffect = sideEffect,
                        onSelect = { index, answer ->
                            onIntent(Intent.OnQuestionAnswered(index, answer))
                        }
                    )
                }

                StretchButton(
                    state = uiState.value.nextButton,
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

            AskNotificationPermission(
                shouldAsk = askNotificationPermission,
                oGranted = {
                    onIntent(Intent.OnNotificationAllow)
                    askNotificationPermission = false
                },
                onDenied = {
                    onIntent(Intent.OnNotificationDeny)
                    askNotificationPermission = false
                }
            )

            SmoothOverlay(
                visible = uiState.value.madeForYou.isVisible,
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
                visible = uiState.value.planSuccess.isVisible,
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
                    askNotificationPermission = true
                }
            }
        }
    }
}
