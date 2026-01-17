package com.fpstudio.stretchreminder.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.fpstudio.stretchreminder.ui.screen.congratulation.CongratulationScreen
import com.fpstudio.stretchreminder.ui.screen.exercise.ExerciseScreen
import com.fpstudio.stretchreminder.ui.screen.exercise.contract.ExerciseScreenContract
import com.fpstudio.stretchreminder.ui.screen.exercise.contract.Playlist
import com.fpstudio.stretchreminder.ui.screen.exercise.contract.PreText
import com.fpstudio.stretchreminder.ui.screen.form.FormScreen
import com.fpstudio.stretchreminder.ui.screen.home.HomeScreen
import com.fpstudio.stretchreminder.ui.screen.intro.IntroScreen
import com.fpstudio.stretchreminder.ui.screen.purchasesuccess.PurchaseSuccessScreen
import com.fpstudio.stretchreminder.ui.screen.threeyes.ThreeYesScreen
import com.fpstudio.stretchreminder.ui.screen.tutorial.TutorialScreen
import com.fpstudio.stretchreminder.ui.screen.routine.RoutineSelectionScreen
import com.fpstudio.stretchreminder.ui.screen.settings.SettingsScreen
import com.fpstudio.stretchreminder.R

@Composable
fun App() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Intro) {

        composable<Intro> {
            IntroScreen(
                onNavigateToForm = {
                    navController.navigate(Form, navOptions = navOptions {
                        popUpTo(Intro) { saveState = true }
                    })
                },
                onNavigateToHome = {
                    navController.navigate(Home, navOptions = navOptions {
                        popUpTo(Intro) { inclusive = true }
                    })
                }
            )
        }

        composable<Form> {
            FormScreen {
                navController.navigate(ThreeYes)
            }
        }

        composable<ThreeYes> {
            ThreeYesScreen {
                navController.navigate(Congratulation)
            }
        }

        composable<Congratulation> {
            CongratulationScreen {
                navController.navigate(Tutorial) {
                    popUpTo(Intro) { inclusive = true }
                }
            }
        }

        composable<Tutorial> {
            TutorialScreen {
                navController.navigate(Premium(fromOnboarding = true)) {
                    popUpTo(Tutorial) { inclusive = true }
                }
            }
        }

        composable<Home> {
            HomeScreen(
                onStretchButtonClick = {
                    navController.navigate(RoutineSelection)
                },
                onMenuClick = {
                    navController.navigate(Settings)
                }
            )
        }
        
        composable<RoutineSelection> {
            RoutineSelectionScreen(
                onNavigateUp = { navController.navigateUp() },
                onContinue = { videos ->
                    navController.navigate(Exercise(videos.map { it.videoUrl }))
                },
                onNavigateToMyRoutines = {
                    // Navigate to My Routines (currently handled within sheet, but if full screen needed)
                },
                onNavigateToPremium = {
                    navController.navigate(Premium(fromOnboarding = false))
                }
            )
        }

        composable<Exercise> {
            // Actually, in Navigation Compose with type-safe routes, 
            // the data is already in the backStackEntry.toRoute<Exercise>()
            val args = it.toRoute<Exercise>()
            
            ExerciseScreen(
                state = ExerciseScreenContract.UiState(
                    playlist = Playlist(
                        videos = args.videoUrls
                    ),
                    preText = PreText(
                        text = R.string.tutorial_get_ready,
                        secondsToShowPreText = 3000,
                        isVisible = true,
                        showPreTextForEachVideo = true
                    ),
                    disclaimer = R.string.tutorial_disclaimer
                )
            ) {
                navController.navigate(Home) {
                    popUpTo(Home) { inclusive = true }
                }
            }
        }
        
        composable<Settings> {
            SettingsScreen(
                onNavigateBack = {
                    navController.navigateUp()
                },
                onNavigateToPremium = {
                    navController.navigate(Premium(fromOnboarding = false))
                },
                onNavigateToFeedback = {
                    navController.navigate(Feedback)
                }
            )
        }
        
        composable<Feedback> {
            com.fpstudio.stretchreminder.ui.screen.feedback.FeedbackScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
        
        composable<Premium> { backStackEntry ->
            val args = backStackEntry.toRoute<Premium>()
            com.fpstudio.stretchreminder.ui.screen.premium.PremiumScreen(
                onNavigateBack = {
                    if (args.fromOnboarding) {
                        navController.navigate(Home) {
                            popUpTo(0) // Clear entire stack
                        }
                    } else {
                        navController.navigateUp()
                    }
                },
                onNavigateToSuccess = {
                    navController.navigate(PremiumSuccess) {
                        if (args.fromOnboarding) {
                            popUpTo(0) // Clear stack for onboarding flow
                        } else {
                            popUpTo(Home) { saveState = true }
                        }
                    }
                }
            )
        }
        
        composable<PremiumSuccess> {
            PurchaseSuccessScreen(
                onCloseClick = {
                    navController.navigate(Home) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onStartStretchingClick = {
                    navController.navigate(Home) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
