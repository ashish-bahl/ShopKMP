package org.example.project.presentation.utils

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun QuantityStepper(
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    min: Int = 1,
    max: Int = Int.MAX_VALUE,
    enabled: Boolean = true,
    buttonSize: Dp = 32.dp,
    spacing: Dp = 8.dp,
    decrementContentDescription: String = "Decrease quantity",
    incrementContentDescription: String = "Increase quantity",
    quantityContentDescription: (Int) -> String = { "Quantity: $it" },
) {
    val canDecrement = enabled && quantity > min
    val canIncrement = enabled && quantity < max
    val decrementInteractionSource = remember { MutableInteractionSource() }
    val incrementInteractionSource = remember { MutableInteractionSource() }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        FilledTonalIconButton(
            onClick = { if (canDecrement) onQuantityChange(quantity - 1) },
            enabled = canDecrement,
            modifier = Modifier
                .size(buttonSize)
                .semantics { contentDescription = decrementContentDescription },
            interactionSource = decrementInteractionSource,
        ) {
            Text(
                text = "-",
                style = MaterialTheme.typography.titleMedium,
            )
        }

        Spacer(modifier = Modifier.width(spacing))

        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .widthIn(min = 28.dp)
                .semantics { contentDescription = quantityContentDescription(quantity) },
        )

        Spacer(modifier = Modifier.width(spacing))

        FilledTonalIconButton(
            onClick = { if (canIncrement) onQuantityChange(quantity + 1) },
            enabled = canIncrement,
            modifier = Modifier
                .size(buttonSize)
                .semantics { contentDescription = incrementContentDescription },
            interactionSource = incrementInteractionSource,
        ) {
            Text(
                text = "+",
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}
