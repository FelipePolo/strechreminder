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
import com.fpstudio.stretchreminder.ui.screen.threeyes.ThreeYesScreen
import com.fpstudio.stretchreminder.ui.screen.tutorial.TutorialScreen
import com.fpstudio.stretchreminder.ui.screen.routine.RoutineSelectionScreen
import com.fpstudio.stretchreminder.ui.screen.settings.SettingsScreen

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
                navController.navigate(Tutorial)
            }
        }

        composable<Tutorial> {
            TutorialScreen {
                navController.navigate(Home)
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
                onBackClick = {
                    navController.navigateUp()
                },
                onContinue = { selectedVideos ->
                    navController.navigate(Exercise(selectedVideos.map {
                        it.videoUrl
                    }))
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
                        text = "Get Ready",
                        secondsToShowPreText = 3000,
                        isVisible = true,
                        showPreTextForEachVideo = true
                    ),
                    disclaimer = "If your experience pain or discomfort while exercising, please stop immediately and consult your doctor or qualified healthcare professional before continuing."
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
                    navController.navigate(Premium)
                }
            )
        }
        
        composable<Premium> {
            com.fpstudio.stretchreminder.ui.screen.premium.PremiumScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}
