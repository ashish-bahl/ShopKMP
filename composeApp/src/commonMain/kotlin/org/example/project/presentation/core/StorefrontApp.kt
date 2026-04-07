package org.example.project.presentation.core

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import org.example.project.presentation.navigation.AppNavigation
import org.example.project.presentation.utils.MyTopAppBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StorefrontApp(navController: NavHostController, windowSizeClass: WindowSizeClass) {
    val mainViewModel = koinViewModel<MainViewModel>()
    val cartItemCount by mainViewModel.cartItemCountFlow.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            MyTopAppBar(navController = navController, cartItemCount = cartItemCount)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        AppNavigation(navController, windowSizeClass, innerPadding)
    }
}