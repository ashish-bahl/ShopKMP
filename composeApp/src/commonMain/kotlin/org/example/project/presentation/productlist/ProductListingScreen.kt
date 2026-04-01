package org.example.project.presentation.productlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.example.project.presentation.navigation.NavigationRoutes
import org.example.project.presentation.utils.ProductListingCard
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import shopkmp.composeapp.generated.resources.Res
import shopkmp.composeapp.generated.resources.error_occurred
import shopkmp.composeapp.generated.resources.no_products_found
import shopkmp.composeapp.generated.resources.retry

@Composable
fun ProductListingScreen(
    navController: NavController,
) {
    val productListingViewModel = koinViewModel<ProductListingViewModel>()

    val state by productListingViewModel.productListingUiState.collectAsStateWithLifecycle()
//    val deviceType = LocalUIDeviceSize.current

    val columns = 2 /*when (deviceType) {
        DeviceType.MOBILE_PORTRAIT -> 2
        DeviceType.MOBILE_LANDSCAPE -> 3
        DeviceType.TABLET_PORTRAIT -> 3
        DeviceType.TABLET_LANDSCAPE -> 4
    }*/

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            if (state.products.isNotEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columns),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(state.products) {
                        ProductListingCard(it, onProductClick = {
                            navController.navigate(NavigationRoutes.ProductDetailsRoute(it.id))
                        })
                    }
                }
            } else if (!state.isLoading && state.errorMessage == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = stringResource(Res.string.no_products_found))
                }
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        if (state.errorMessage != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = state.errorMessage ?: stringResource(Res.string.error_occurred),
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { productListingViewModel.fetchAllProducts() }) {
                    Text(text = stringResource(Res.string.retry))
                }
            }
        }
    }
}