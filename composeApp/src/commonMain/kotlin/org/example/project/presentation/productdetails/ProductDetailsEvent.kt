package org.example.project.presentation.productdetails

import org.example.project.domain.model.Product

sealed interface ProductDetailsEvent {
    data class AddToCart(val product: Product) : ProductDetailsEvent

    data class UpdateCartCount(val count: Int) : ProductDetailsEvent

    data class LoadProduct(val id: String) : ProductDetailsEvent
}
