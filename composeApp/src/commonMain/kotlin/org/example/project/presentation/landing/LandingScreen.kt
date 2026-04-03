package org.example.project.presentation.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.example.project.data.source.dummyProducts
import org.example.project.domain.model.Product
import org.example.project.presentation.navigation.LocalUIDeviceSize
import org.example.project.presentation.navigation.NavigationRoutes
import org.example.project.presentation.utils.DeviceType
import org.example.project.presentation.utils.ProductListingCard
import org.jetbrains.compose.resources.stringResource
import shopkmp.composeapp.generated.resources.Res
import shopkmp.composeapp.generated.resources.discover_msg
import shopkmp.composeapp.generated.resources.featured_products
import shopkmp.composeapp.generated.resources.start_shopping
import shopkmp.composeapp.generated.resources.welcome_msg

@Composable
fun LandingScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    val deviceSize = LocalUIDeviceSize.current
    val featuredProducts = dummyProducts.take(4)

    when (deviceSize) {
        DeviceType.MOBILE_PORTRAIT, DeviceType.TABLET_PORTRAIT -> {
            Column(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .verticalScroll(scrollState)
                    .fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                WelcomeSection(onStartShopping = {
                    navController.navigate(NavigationRoutes.ProductListingRoute)
                })

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = stringResource(Res.string.featured_products),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(16.dp))

                StaticVerticalGrid(items = featuredProducts, onProductClick = { productEntity ->
                    navController.navigate(NavigationRoutes.ProductDetailsRoute(productEntity.id))
                })
            }
        }

        DeviceType.MOBILE_LANDSCAPE, DeviceType.TABLET_LANDSCAPE -> {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(scrollState),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 16.dp)
                ) {
                    WelcomeSection(onStartShopping = {
                        navController.navigate(NavigationRoutes.ProductListingRoute)
                    })
                }

                Column(
                    modifier = Modifier
                        .weight(1.5f)
                        .padding(vertical = 16.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.featured_products),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    StaticVerticalGrid(
                        items = featuredProducts,
                        columns = 2,
                        onProductClick = { productEntity ->
                            navController.navigate(
                                NavigationRoutes.ProductDetailsRoute(
                                    productEntity.id
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun WelcomeSection(onStartShopping: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Text(
            text = stringResource(Res.string.welcome_msg),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(Res.string.discover_msg),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onStartShopping,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(Res.string.start_shopping))
        }
    }


}

@Composable
private fun StaticVerticalGrid(
    items: List<Product>,
    columns: Int = 2,
    onProductClick: (productEntity: Product) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items.chunked(columns).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowItems.forEach { item ->
                    Box(modifier = Modifier.weight(1f)) {
                        ProductListingCard(item, onProductClick = {
                            onProductClick(item)
                        })
                    }
                }
                // Fill empty cells if last row is incomplete
                if (rowItems.size < columns) {
                    repeat(columns - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
