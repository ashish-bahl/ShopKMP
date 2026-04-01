package org.example.project.presentation.productdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.source.dummyProducts
import org.example.project.domain.model.Product
import org.example.project.domain.usecase.cart.AddToCartUseCase
import org.example.project.domain.usecase.cart.GetCartItemUseCase
import org.example.project.domain.usecase.cart.RemoveFromCartUseCase
import org.example.project.domain.usecase.cart.UpdateCartItemUseCase

class ProductDetailsViewModel (
    val addToCartUseCase: AddToCartUseCase,
    val getCartProductUseCase: GetCartItemUseCase,
    val updateCartUseCase: UpdateCartItemUseCase,
    val deleteFromCartUseCase: RemoveFromCartUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailsUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: ProductDetailsEvent) {
        when (event) {
            is ProductDetailsEvent.AddToCart -> addToCart(event.product)
            is ProductDetailsEvent.UpdateCartCount -> updateCartProductCount(event.count)
            is ProductDetailsEvent.LoadProduct -> getCartProduct(event.id)
        }
    }


    private fun getCartProduct(id: String) {
        viewModelScope.launch {
            val result = getCartProductUseCase(id)
            val product: Product? = result ?: dummyProducts.find { it.id == id }
            _uiState.update {
                it.copy(product = product)
            }
        }
    }

    private fun deleteFromCart(product: Product) {
        viewModelScope.launch {
            deleteFromCartUseCase(product)
            val updatedEntity = uiState.value.product?.copy(quantity = 0)
            _uiState.update {
                it.copy(product = updatedEntity)
            }
        }
    }

    private fun updateCartProductCount(count: Int) {

        val updatedEntity = uiState.value.product?.copy(quantity = count)
        updatedEntity?.let {
            if (count >= 1) {
                viewModelScope.launch {
                    val result = viewModelScope.async {
                        updateCartUseCase(it)
                    }
                    result.await()
                    _uiState.update { state ->
                        state.copy(product = state.product?.copy(quantity = count))
                    }
                }
            } else {
                deleteFromCart(updatedEntity)
            }

        }
    }

    private fun addToCart(product: Product) {
        viewModelScope.launch {
            val result = addToCartUseCase(product.copy(quantity = 1))
            if (result > 0) {
                val quantity = _uiState.value.product?.quantity ?: 0
                _uiState.update {
                    it.copy(product = product.copy(quantity = quantity + 1))
                }
            }
        }

    }

}