package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.storefront.ui.theme.MyApplicationTheme
import org.example.project.presentation.navigation.AppNavigation
import org.example.project.presentation.utils.MyTopAppBar

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            MyApplicationTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = { MyTopAppBar(navController, 0) },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    AppNavigation(navController, windowSizeClass, innerPadding)
                }
            }
        }
    }
}

/*
@Preview
@Composable
fun AppAndroidPreview() {
    App()
}*/
