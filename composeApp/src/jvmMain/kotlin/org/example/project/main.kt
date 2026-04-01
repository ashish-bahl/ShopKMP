package org.example.project

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ShopKMP",
    ) {
        App(
            batteryManager = remember {
                BatteryManager()
            }
        )
    }
}