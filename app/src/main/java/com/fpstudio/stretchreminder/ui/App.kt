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
import com.fpstudio.stretchreminder.ui.screen.IntroScreen

@Composable
fun App() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Intro) {
        composable<Intro> {
            IntroScreen()
        }
        composable<Form> {
            Greeting("Form") {
                navController.navigate(Home)
            }
        }
        composable<Home> {
            Greeting("Home") {
                navController.navigateUp()
            }
        }
    }


}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Text(
            text = "Hello $name!",
            modifier = modifier
                .padding(innerPadding)
                .clickable(onClick = onClick)
        )
    }
}