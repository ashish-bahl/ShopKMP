package org.example.project.presentation.order.summary

import org.example.project.domain.model.Order

data class OrderSummaryState(
    val isLoading: Boolean = true,
    val order: Order? = null,
    val errorMessage: String? = null,
)
