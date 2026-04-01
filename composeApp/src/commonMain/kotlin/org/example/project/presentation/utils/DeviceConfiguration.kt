package org.example.project.presentation.utils

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

enum class DeviceType {
    MOBILE_PORTRAIT,
    MOBILE_LANDSCAPE,
    TABLET_PORTRAIT,
    TABLET_LANDSCAPE,
}


object DeviceSizeConfiguration {

    fun classify(
        widthClass: WindowWidthSizeClass,
        heightClass: WindowHeightSizeClass
    ): DeviceType {
        return when (widthClass) {

            WindowWidthSizeClass.Medium -> {
                if (isLandscape(widthClass, heightClass)) {
                    DeviceType.TABLET_LANDSCAPE
                } else {
                    DeviceType.TABLET_PORTRAIT
                }
            }

            // Compact → treat as MOBILE
            WindowWidthSizeClass.Compact -> {
                if (isLandscape(widthClass, heightClass)) {
                    DeviceType.MOBILE_LANDSCAPE
                } else {
                    DeviceType.MOBILE_PORTRAIT
                }
            }

            else -> {
                // Fallback to mobile classification
                if (isLandscape(widthClass, heightClass)) {
                    DeviceType.MOBILE_LANDSCAPE
                } else {
                    DeviceType.MOBILE_PORTRAIT
                }
            }
        }
    }

   private fun isLandscape(
        widthClass: WindowWidthSizeClass,
        heightClass: WindowHeightSizeClass
    ): Boolean = type(widthClass) >= type(heightClass)

    private fun type(w: WindowWidthSizeClass): Int = when (w) {
        WindowWidthSizeClass.Compact -> 0
        WindowWidthSizeClass.Medium -> 1
        WindowWidthSizeClass.Expanded -> 2
        else -> 0
    }

    private fun type(h: WindowHeightSizeClass): Int = when (h) {
        WindowHeightSizeClass.Compact -> 0
        WindowHeightSizeClass.Medium -> 1
        WindowHeightSizeClass.Expanded -> 2
        else -> 0
    }


}
