package org.example.project

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.window.ComposeUIViewController
import androidx.navigation.compose.rememberNavController
import org.example.project.di.initKoin
import org.example.project.presentation.core.StorefrontApp

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    val navController = rememberNavController()
    val windowSizeClass = calculateWindowSizeClass()
    StorefrontApp(navController = navController, windowSizeClass = windowSizeClass)
}
