package org.example.project.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.model.Product
import org.example.project.domain.repository.CartRepository

class CartViewModel (
    private val cartRepo: CartRepository
) : ViewModel() {

    private val _cartUiState = MutableStateFlow(CartUiState())
    val cartUiState = _cartUiState.asStateFlow()

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        viewModelScope.launch {
            _cartUiState.update { it.copy(isLoading = true) }
            cartRepo.observeCartItems().collect { cartItems ->
                updateCartState(cartItems)
            }
        }
    }

    fun incrementQuantity(product: Product) {
        viewModelScope.launch {
            val updatedProduct = product.copy(quantity = product.quantity + 1)
            cartRepo.update(updatedProduct)
        }
    }

    fun decrementQuantity(product: Product) {
        viewModelScope.launch {
            if (product.quantity > 1) {
                val updatedProduct = product.copy(quantity = product.quantity - 1)
                cartRepo.update(updatedProduct)
            } else {
                // Remove item when quantity reaches 0
                cartRepo.remove(product)
            }
        }
    }

    fun removeItem(product: Product) {
        viewModelScope.launch {
            cartRepo.remove(product)
        }
    }

    private fun updateCartState(items: List<Product>) {
        val totalQuantity = items.sumOf { it.quantity }
        val totalAmount = items.sumOf { it.sellingPrice * it.quantity }
        _cartUiState.update {
            it.copy(
                isLoading = false,
                cartItems = items,
                totalQuantity = totalQuantity,
                totalAmount = totalAmount
            )
        }
    }
}