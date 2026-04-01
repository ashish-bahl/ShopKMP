package org.example.project

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import androidx.navigation.compose.rememberNavController
import org.example.project.presentation.utils.MyTopAppBar
import org.example.project.presentation.navigation.AppNavigation

fun MainViewController() = ComposeUIViewController {
    val navController = rememberNavController()
    val dao = remember {
        getStorefrontDatabase().storefrontDao()
    }
    Scaffold(
        topBar = { MyTopAppBar(navController = navController, cartItemCount = 0) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        AppNavigation(
            navController = navController,
            innerPadding = innerPadding,
            dao = dao
        )
    }
}
