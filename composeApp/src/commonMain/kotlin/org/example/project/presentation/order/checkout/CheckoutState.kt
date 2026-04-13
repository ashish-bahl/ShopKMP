package org.example.project.presentation.order.checkout

import org.example.project.domain.model.Product

data class CheckoutState(
    val cartItems: List<Product> = emptyList(),
    val totalAmount: Double = 0.0,
    val totalQuantity: Int = 0,
    val name :String = "",
    val address :String = "",
    val number :String = "",
    val nameErrorMessage :String = "",
    val addressErrorMessage :String = "",
    val numberErrorMessage :String = "",
)
