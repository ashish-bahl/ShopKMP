package org.example.project.presentation.productlist

import org.example.project.domain.model.Product

data class ProductListUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val products: List<Product> = emptyList(),
)
