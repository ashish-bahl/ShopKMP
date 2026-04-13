package org.example.project.presentation.order.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.example.project.domain.model.Product
import org.example.project.presentation.navigation.LocalUIDeviceSize
import org.example.project.presentation.navigation.NavigationRoutes
import org.example.project.presentation.utils.DeviceType
import org.example.project.presentation.utils.prependRupeeSymbol
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import shopkmp.composeapp.generated.resources.Res
import shopkmp.composeapp.generated.resources.address
import shopkmp.composeapp.generated.resources.full_name
import shopkmp.composeapp.generated.resources.order_summary
import shopkmp.composeapp.generated.resources.pay
import shopkmp.composeapp.generated.resources.phone_number
import shopkmp.composeapp.generated.resources.shipping_details
import shopkmp.composeapp.generated.resources.total
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun CheckoutScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<CheckoutViewModel>()
    val state by viewModel.checkoutState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getCartProducts()

        viewModel.checkoutEffect.collect {
            when(it){
                is CheckoutEffect.NavigateToOrderSummary ->{
                    navController.navigate(NavigationRoutes.OrderSummaryRoute(it.id.toString())) {
                        popUpTo<NavigationRoutes.LandingRoute> {
                            inclusive = false
                        }
                    }
                }
            }
        }
    }

    CheckOutView(
        state = state,
        modifier = modifier,
        onNameChange = viewModel::updateName,
        onAddressChange = viewModel::updateAddress,
        onNumberChange = viewModel::updateNumber,
        onPayCLicked = viewModel::placeOrder
    )
}

@Composable
fun CheckOutView(
    state: CheckoutState,
    modifier: Modifier = Modifier,
    onNameChange: (String) -> Unit = {},
    onAddressChange: (String) -> Unit = {},
    onNumberChange: (String) -> Unit = {},
    onPayCLicked :() -> Unit = {}
) {
    val deviceSize = LocalUIDeviceSize.current

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        Spacer(Modifier.height(16.dp))

        if (deviceSize == DeviceType.MOBILE_PORTRAIT || deviceSize == DeviceType.TABLET_PORTRAIT) {

            ShippingDetails(
                state = state,
                onNameChange = onNameChange,
                onAddressChange = onAddressChange,
                onNumberChange = onNumberChange
            )

            Spacer(Modifier.height(32.dp))

            OrderSummary(state.cartItems, state.totalAmount.toString())

            Spacer(modifier = Modifier.weight(1f))
        } else {
            Row(Modifier.fillMaxWidth()) {
                ShippingDetails(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    state = state,
                    onNameChange = onNameChange,
                    onAddressChange = onAddressChange,
                    onNumberChange = onNumberChange
                )

                Spacer(Modifier.width(16.dp))

                OrderSummary(
                    products = state.cartItems,
                    totalAmount = state.totalAmount.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }

        Button(
            onPayCLicked,
            colors = ButtonDefaults.buttonColors()
                .copy(containerColor = MaterialTheme.colorScheme.inverseSurface),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(56.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                stringResource(Res.string.pay, (state.totalAmount).toString().prependRupeeSymbol()),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.inverseOnSurface
                )
            )
        }
    }
}

@Composable
fun ShippingDetails(
    state: CheckoutState,
    modifier: Modifier = Modifier,
    onNameChange: (String) -> Unit = {},
    onAddressChange: (String) -> Unit = {},
    onNumberChange: (String) -> Unit = {},
) {
    Surface {
        Column(modifier = modifier) {
            Text(
                stringResource(Res.string.shipping_details),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold
                )
            )

            Spacer(Modifier.height(16.dp))

            Text(
                stringResource(Res.string.full_name),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold
                )
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.name,
                onValueChange = { onNameChange(it) },
                colors = TextFieldDefaults.colors().copy(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                isError = state.nameErrorMessage.isNotBlank(),
                supportingText = {
                    if(state.nameErrorMessage.isNotBlank())
                        Text(state.nameErrorMessage)
                }

            )

            Spacer(Modifier.height(16.dp))

            Text(
                stringResource(Res.string.address), style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold
                )
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.address,
                onValueChange = { onAddressChange(it) },
                colors = TextFieldDefaults.colors().copy(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                isError = state.addressErrorMessage.isNotBlank(),
                supportingText = {
                    if(state.addressErrorMessage.isNotBlank())
                        Text(state.addressErrorMessage)
                }
            )

            Spacer(Modifier.height(16.dp))

            Text(
                stringResource(Res.string.phone_number),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold
                )
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.number,
                onValueChange = { onNumberChange(it) },
                colors = TextFieldDefaults.colors().copy(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                isError = state.numberErrorMessage.isNotBlank(),
                supportingText = {
                    if(state.numberErrorMessage.isNotBlank())
                        Text(state.numberErrorMessage)
                }
            )
        }
    }
}

@Composable
fun OrderSummary(
    products: List<Product>,
    totalAmount: String,
    modifier: Modifier = Modifier
) {
    Surface {
        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(
                containerColor = Color.Gray.copy(alpha = 0.1f),
            ),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    stringResource(Res.string.order_summary),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold
                    )
                )

                Spacer(Modifier.height(16.dp))

                ProductList(products)

                Spacer(Modifier.height(16.dp))

                HorizontalDivider(
                    Modifier
                        .height(1.dp)
                        .background(Color.Gray)
                )

                Spacer(Modifier.height(16.dp))

                Row {
                    Text(
                        text = stringResource(Res.string.total),
                        modifier = Modifier.weight(0.7f),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = totalAmount.prependRupeeSymbol(),
                        modifier = Modifier.weight(0.3f),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }


            }
        }
    }
}

@Composable
fun ProductList(products: List<Product>, modifier: Modifier = Modifier) {
    repeat(products.size) {
        with(products[it]) {
            Row(modifier = modifier) {
                Text(name)
                Spacer(Modifier.width(8.dp))
                Text("x $quantity")
                Spacer(Modifier.weight(1f))
                val itemTotal = quantity * sellingPrice
                Text(itemTotal.toString())
            }
        }
    }

}
