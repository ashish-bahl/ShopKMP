package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.storefront.ui.theme.MyApplicationTheme
import org.example.project.presentation.utils.MyTopAppBar
import org.example.project.presentation.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val dao = remember {
                getStorefrontDatabase(this).storefrontDao()
            }
            /*App(batteryManager = remember {
                BatteryManager(applicationContext)
            })*/
            MyApplicationTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = { MyTopAppBar(navController, 0) },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    AppNavigation(navController, innerPadding, dao)
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
