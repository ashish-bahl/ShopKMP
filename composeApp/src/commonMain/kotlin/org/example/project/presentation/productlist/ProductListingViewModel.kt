package org.example.project.presentation.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.model.NetworkResult
import org.example.project.domain.model.Product
import org.example.project.domain.usecase.product.LoadProductsUseCase

class ProductListingViewModel (
    private val loadProductsUseCase: LoadProductsUseCase
) : ViewModel() {

    private val _productListingUiState = MutableStateFlow(ProductListUiState())
    val productListingUiState = _productListingUiState.asStateFlow()

    init {
        fetchAllProducts()
    }

    fun fetchAllProducts() {
        viewModelScope.launch {
            _productListingUiState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val networkResponse = loadProductsUseCase()) {
                is NetworkResult.Success<List<Product>> -> {
                    _productListingUiState.update {
                        it.copy(
                            isLoading = false,
                            products = networkResponse.data ?: emptyList()
                        )
                    }
                }

                is NetworkResult.Exception -> {
                    _productListingUiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = networkResponse.message
                        )
                    }
                }

                is NetworkResult.NetworkError -> {
                    _productListingUiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Network error. Please check your connection."
                        )
                    }
                }
            }
        }
    }
}
