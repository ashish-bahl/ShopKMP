package org.example.project.presentation.order.checkout

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

sealed interface CheckoutEffect{
    data class NavigateToOrderSummary @OptIn(ExperimentalUuidApi::class) constructor(val id : Uuid) : CheckoutEffect
}