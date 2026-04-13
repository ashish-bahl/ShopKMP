package org.example.project.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.example.project.presentation.cart.CartScreen
import org.example.project.presentation.landing.LandingScreen
import org.example.project.presentation.order.checkout.CheckoutScreen
import org.example.project.presentation.order.summary.OrderSummaryScreen
import org.example.project.presentation.productdetails.ProductDetailsScreen
import org.example.project.presentation.productlist.ProductListingScreen
import org.example.project.presentation.utils.DeviceSizeConfiguration
import org.example.project.presentation.utils.DeviceType

val LocalUIDeviceSize = staticCompositionLocalOf<DeviceType> {
    error("UiDeviceBucket not provided")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
    innerPadding: PaddingValues,
) {

    val uiDeviceClassifier = DeviceSizeConfiguration.classify(
        windowSizeClass.widthSizeClass, windowSizeClass.heightSizeClass
    )

    CompositionLocalProvider(LocalUIDeviceSize provides uiDeviceClassifier) {
        NavHost(
            navController = navController,
            startDestination = NavigationRoutes.LandingRoute,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable<NavigationRoutes.LandingRoute> {
                LandingScreen(navController)
            }

            composable<NavigationRoutes.ProductListingRoute> {
                ProductListingScreen(navController)
            }

            composable<NavigationRoutes.ProductDetailsRoute> {
                val properties = it.toRoute<NavigationRoutes.ProductDetailsRoute>()
                ProductDetailsScreen(properties.id)
            }

            composable<NavigationRoutes.CartRoute> {
                CartScreen(navController)
            }
            composable<NavigationRoutes.CheckoutRoute> {
                CheckoutScreen(navController)
            }

            composable<NavigationRoutes.OrderSummaryRoute> {
                val properties = it.toRoute<NavigationRoutes.OrderSummaryRoute>()
                OrderSummaryScreen(navController,properties.id)
            }

        }
    }
}