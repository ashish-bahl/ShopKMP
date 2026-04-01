package org.example.project.presentation.navigation

import kotlinx.serialization.Serializable

sealed class NavigationRoutes {
    @Serializable
    data object LandingRoute : NavigationRoutes()

    @Serializable
    data object ProductListingRoute : NavigationRoutes()

    @Serializable
    data class ProductDetailsRoute(val id: String) : NavigationRoutes()

    @Serializable
    data object CartRoute : NavigationRoutes()

    @Serializable
    data object CheckoutRoute : NavigationRoutes()

    @Serializable
    data class OrderSummaryRoute(val id : String) : NavigationRoutes()
}