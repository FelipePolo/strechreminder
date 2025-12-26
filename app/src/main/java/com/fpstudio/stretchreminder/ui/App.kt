package com.fpstudio.stretchreminder.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.fpstudio.stretchreminder.ui.screen.congratulation.CongratulationScreen
import com.fpstudio.stretchreminder.ui.screen.form.FormScreen
import com.fpstudio.stretchreminder.ui.screen.home.HomeScreen
import com.fpstudio.stretchreminder.ui.screen.intro.IntroScreen
import com.fpstudio.stretchreminder.ui.screen.threeyes.ThreeYesScreen
import com.fpstudio.stretchreminder.ui.screen.exerciseroutine.ExerciseRoutineScreen
import com.fpstudio.stretchreminder.ui.screen.routine.RoutineSelectionScreen

@Composable
fun App() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Intro) {

        composable<Intro> {
            IntroScreen {
                navController.navigate(Form, navOptions = navOptions {
                    popUpTo(Intro) { saveState = true }
                })
            }
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
                navController.navigate(ExerciseRoutine)
            }
        }

        composable<ExerciseRoutine> {
            ExerciseRoutineScreen {
                navController.navigate(Home)
            }
        }

        composable<Home> {
            HomeScreen(
                onStretchButtonClick = {
                    navController.navigate(RoutineSelection)
                }
            )
        }
        
        composable<RoutineSelection> {
            RoutineSelectionScreen(
                onBackClick = {
                    navController.navigateUp()
                },
                onContinue = { selectedVideos ->
                    // TODO: Navigate to exercise screen with selected videos
                    // navController.navigate(Exercise(selectedVideos.map { it.id }))
                }
            )
        }
    }
}
