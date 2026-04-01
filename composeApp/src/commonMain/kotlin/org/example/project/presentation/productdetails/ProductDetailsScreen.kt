package org.example.project.presentation.productdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.example.project.data.local.dao.StorefrontDao
import org.example.project.data.repository.DefaultCartRepository
import org.example.project.domain.model.Product
import org.example.project.domain.usecase.cart.AddToCartUseCase
import org.example.project.domain.usecase.cart.GetCartItemUseCase
import org.example.project.domain.usecase.cart.RemoveFromCartUseCase
import org.example.project.domain.usecase.cart.UpdateCartItemUseCase
import org.example.project.presentation.utils.QuantityStepper
import org.example.project.presentation.utils.prependRupeeSymbol
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import shopkmp.composeapp.generated.resources.Res
import shopkmp.composeapp.generated.resources.add_to_cart
import shopkmp.composeapp.generated.resources.compose_multiplatform

@Composable
fun ProductDetailsScreen(
    id: String,
    dao: StorefrontDao
) {

    val repository = DefaultCartRepository(dao)
    val viewModel: ProductDetailsViewModel = remember {
        ProductDetailsViewModel(
            addToCartUseCase = AddToCartUseCase(repository),
            getCartProductUseCase = GetCartItemUseCase(repository),
            updateCartUseCase = UpdateCartItemUseCase(repository),
            deleteFromCartUseCase = RemoveFromCartUseCase(repository)
        )
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(ProductDetailsEvent.LoadProduct(id))
    }

    val viewState = viewModel.uiState.collectAsStateWithLifecycle()

    ProductDetailsView(
        productDetailsState = viewState,
        addToCartClick = {
            viewModel.onEvent(ProductDetailsEvent.AddToCart(it))
        }, onQuantityChange = {
            viewModel.onEvent(ProductDetailsEvent.UpdateCartCount(it))
        }
    )
}

@Composable
fun ProductDetailsView(
    productDetailsState: State<ProductDetailsUiState>,
    addToCartClick: (Product) -> Unit,
    onQuantityChange: (Int) -> Unit
) {
//    val deviceSize = LocalUIDeviceSize.current
//    val scrollState = rememberScrollState()
    val productEntity: Product? = productDetailsState.value.product

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /*when (deviceSize) {
                DeviceType.MOBILE_PORTRAIT, DeviceType.TABLET_PORTRAIT -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(bottom = 100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {*/
            ProductImageSection(productEntity)
            ProductInfoSection(productEntity)
//                    }
            /*}

            DeviceType.MOBILE_LANDSCAPE, DeviceType.TABLET_LANDSCAPE -> {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(scrollState),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 16.dp)
                    ) {
                        ProductImageSection(productEntity)
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 16.dp)
                    ) {
                        ProductInfoSection(productEntity)
                        Spacer(modifier = Modifier.height(24.dp))
                        AddToCartButton(
                            productEntity,
                            modifier = Modifier.fillMaxWidth(),
                            addToCartClick = {
                                if (productEntity != null) {
                                    addToCartClick(productEntity)
                                }
                            },
                            onQuantityChange = onQuantityChange
                        )
                    }
                }
            }
        }*/
        }

        BottomActionCard(productEntity, addToCartClick = {
            if (productEntity != null) {
                addToCartClick(productEntity)
            }
        }, onQuantityChange)
        /*if (deviceSize == DeviceType.MOBILE_PORTRAIT || deviceSize == DeviceType.TABLET_PORTRAIT) {
            //place bottom action card here
        }*/
    }
}

@Composable
fun ProductImageSection(productEntity: Product?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .padding(10.dp)
            .background(color = Color.Yellow, shape = RoundedCornerShape(22.dp))
    ) {
        AsyncImage(
            model = productEntity?.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(Res.drawable.compose_multiplatform),
            error = painterResource(Res.drawable.compose_multiplatform),
        )
    }
}

@Composable
fun ProductInfoSection(productEntity: Product?) {
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(10.dp)) {
        Text(productEntity?.name ?: "", style = MaterialTheme.typography.headlineMedium)
        Row {
            Text(
                productEntity?.sellingPrice?.toString()?.prependRupeeSymbol() ?: "",
                style = MaterialTheme.typography.headlineSmall.copy(color = Color(0xFF12651B))
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                productEntity?.costPrice?.toString()?.prependRupeeSymbol() ?: "",
                textDecoration = TextDecoration.LineThrough,
                style = MaterialTheme.typography.titleMedium.copy(color = Color.Red)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            productEntity?.description ?: "",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
        )
    }
}

@Composable
fun BoxScope.BottomActionCard(
    productEntity: Product?,
    addToCartClick: () -> Unit,
    onQuantityChange: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .align(Alignment.BottomCenter),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            AddToCartButton(
                productEntity = productEntity,
                modifier = Modifier.fillMaxWidth(0.8f),
                addToCartClick = addToCartClick,
                onQuantityChange = onQuantityChange
            )
        }
    }
}

@Composable
fun AddToCartButton(
    productEntity: Product?,
    modifier: Modifier = Modifier,
    addToCartClick: () -> Unit,
    onQuantityChange: (Int) -> Unit
) {
    Button(
        modifier = modifier.height(48.dp),
        colors = ButtonColors(
            containerColor = Color.Black,
            contentColor = Color.White,
            disabledContentColor = Color.White,
            disabledContainerColor = Color.Black
        ),
        onClick = {
            addToCartClick()
        },
    ) {
        productEntity?.let {
            if (it.quantity > 0) {
                QuantityStepper(
                    quantity = it.quantity,
                    onQuantityChange = onQuantityChange,
                    min = 0,
                )
            } else {
                Text(stringResource(Res.string.add_to_cart))
            }
        }
    }
}
