package org.example.project.presentation.cart

import org.example.project.domain.model.Product

data class CartUiState(
    val isLoading: Boolean = false,
    val cartItems: List<Product> = emptyList(),
    val totalAmount: Double = 0.0,
    val totalQuantity: Int = 0,
)
