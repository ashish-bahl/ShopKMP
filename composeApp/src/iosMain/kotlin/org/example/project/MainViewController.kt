package org.example.project

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import androidx.navigation.compose.rememberNavController
import org.example.project.di.initKoin
import org.example.project.presentation.utils.MyTopAppBar
import org.example.project.presentation.navigation.AppNavigation

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    val navController = rememberNavController()
    val windowSizeClass = calculateWindowSizeClass()
    Scaffold(
        topBar = { MyTopAppBar(navController = navController, cartItemCount = 0) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        AppNavigation(
            navController = navController,
            innerPadding = innerPadding,
            windowSizeClass = windowSizeClass
        )
    }
}
