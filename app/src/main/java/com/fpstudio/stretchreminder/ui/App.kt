package com.fpstudio.stretchreminder.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fpstudio.stretchreminder.ui.screen.form.FormScreen
import com.fpstudio.stretchreminder.ui.screen.home.HomeScreen
import com.fpstudio.stretchreminder.ui.screen.intro.IntroScreen
import com.fpstudio.stretchreminder.ui.screen.threeyes.ThreeYesContent
import com.fpstudio.stretchreminder.ui.screen.threeyes.ThreeYesScreen

@Composable
fun App() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Intro) {
        composable<Intro> {
            IntroScreen {
                navController.navigate(Form)
            }
        }
        composable<Form> {
            FormScreen {
                navController.navigate(ThreeYes)
            }
        }
        composable<ThreeYes> {
            ThreeYesScreen {
                navController.navigate(Home)
            }
        }
        composable<Home> {
            HomeScreen(
                onStretchButtonClick = {
                    // Aquí puedes agregar la navegación a la pantalla de estiramiento
                }
            )
        }
    }
}
