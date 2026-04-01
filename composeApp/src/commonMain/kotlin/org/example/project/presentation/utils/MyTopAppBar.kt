package org.example.project.presentation.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.example.project.presentation.navigation.NavigationRoutes
import org.jetbrains.compose.resources.stringResource
import shopkmp.composeapp.generated.resources.Res
import shopkmp.composeapp.generated.resources.back_button
import shopkmp.composeapp.generated.resources.cart
import shopkmp.composeapp.generated.resources.checkout
import shopkmp.composeapp.generated.resources.ecommerce_store
import shopkmp.composeapp.generated.resources.order_summary
import shopkmp.composeapp.generated.resources.product_details
import shopkmp.composeapp.generated.resources.products

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    navController: NavHostController,
    cartItemCount: Int
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val productDetailsBase = NavigationRoutes.ProductDetailsRoute::class.qualifiedName ?: ""
    val landingRoute = NavigationRoutes.LandingRoute::class.qualifiedName
    val cartRoute = NavigationRoutes.CartRoute::class.qualifiedName

    val title = when {
        currentRoute == landingRoute -> stringResource(Res.string.ecommerce_store)
        currentRoute == NavigationRoutes.ProductListingRoute::class.qualifiedName -> stringResource(Res.string.products)
        currentRoute == cartRoute -> stringResource(Res.string.cart)
        currentRoute == NavigationRoutes.CheckoutRoute::class.qualifiedName -> stringResource(Res.string.checkout)
        currentRoute == NavigationRoutes.OrderSummaryRoute::class.qualifiedName -> stringResource(Res.string.order_summary)
        currentRoute?.startsWith(productDetailsBase) == true -> stringResource(Res.string.product_details)
        else -> ""
    }

    val canPop by remember(currentRoute) {
        derivedStateOf {
            currentRoute != landingRoute && currentRoute != null
        }
    }

    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            if (canPop) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(Res.string.back_button)
                    )
                }
            }
        },
        actions = {
            if (currentRoute != cartRoute) {
                IconButton(onClick = { navController.navigate(NavigationRoutes.CartRoute) }) {
                    BadgedBox(
                        badge = {
                            if (cartItemCount > 0) {
                                Badge {
                                    Text(
                                        text = if (cartItemCount > 99) "99+" else cartItemCount.toString(),
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = stringResource(Res.string.cart)
                        )
                    }
                }
            }
        }
    )
}