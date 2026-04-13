package org.example.project.presentation.order.summary

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.example.project.presentation.navigation.NavigationRoutes
import org.example.project.presentation.order.checkout.OrderSummary
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import shopkmp.composeapp.generated.resources.Res
import shopkmp.composeapp.generated.resources.continue_shopping
import shopkmp.composeapp.generated.resources.error_occurred
import shopkmp.composeapp.generated.resources.green_check_mark_verified_circle
import shopkmp.composeapp.generated.resources.order_placed_successfully
import shopkmp.composeapp.generated.resources.thank_you_for_your_purchase_your_order_id_is

@Composable
fun OrderSummaryScreen(
    navController: NavController,
    orderId: String,
    modifier: Modifier = Modifier,
) {

    val viewModel: OrderSummaryViewModel = koinViewModel<OrderSummaryViewModel>()
    val state by viewModel.orderSummaryState.collectAsStateWithLifecycle()

    LaunchedEffect(orderId) {
        viewModel.getOrder(orderId)
    }

    Surface(modifier = modifier.fillMaxSize()) {
        // This screen can be reopened from a deep link or after process recreation,
        // so the UI needs explicit loading and missing-order states.
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            state.order == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = state.errorMessage ?: stringResource(Res.string.error_occurred),
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                    )
                }
            }

            else -> {
                val order = requireNotNull(state.order)

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.green_check_mark_verified_circle),
                        contentDescription = null,
                        tint = Color.Green,
                        modifier = Modifier
                            .size(50.dp, 50.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(
                        stringResource(Res.string.order_placed_successfully),
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        ),
                    )

                    Spacer(Modifier.height(16.dp))

                    val text = buildAnnotatedString {
                        withStyle(
                            MaterialTheme.typography.bodyMedium.toSpanStyle()
                        ) {
                            append(stringResource(Res.string.thank_you_for_your_purchase_your_order_id_is))
                        }

                        withStyle(
                            MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold
                            ).toSpanStyle()
                        ) {
                            append(order.id)
                        }
                    }

                    Text(text)

                    Spacer(Modifier.height(32.dp))

                    OrderSummary(order.cartItems, order.totalAmount.toString())

                    Spacer(Modifier.height(32.dp))
                    Spacer(Modifier.weight(1f))

                    Button(
                        {
                            navController.navigate(NavigationRoutes.LandingRoute) {
                                popUpTo<NavigationRoutes.OrderSummaryRoute> {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(56.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors()
                            .copy(containerColor = MaterialTheme.colorScheme.inverseSurface)
                    ) {
                        Text(
                            text = stringResource(Res.string.continue_shopping),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.inverseOnSurface,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}
