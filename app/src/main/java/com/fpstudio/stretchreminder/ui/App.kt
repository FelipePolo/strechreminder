package com.fpstudio.stretchreminder.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fpstudio.stretchreminder.ui.screen.form.FormScreen
import com.fpstudio.stretchreminder.ui.screen.home.HomeScreen
import com.fpstudio.stretchreminder.ui.screen.intro.IntroScreen
import org.koin.compose.koinInject

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
            FormScreen{
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
