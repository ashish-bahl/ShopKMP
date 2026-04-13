package org.example.project.presentation.order.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.usecase.order.GetOrderUseCase

class OrderSummaryViewModel(
    private val getOrderUseCase: GetOrderUseCase
) : ViewModel() {

    private val _orderSummaryState = MutableStateFlow(OrderSummaryState())
    val orderSummaryState = _orderSummaryState.asStateFlow()

    fun getOrder(id: String) {
        viewModelScope.launch {
            _orderSummaryState.update {
                it.copy(
                    isLoading = true,
                    order = null,
                    errorMessage = null,
                )
            }
            val order = getOrderUseCase.invoke(id)
            if (order != null) {
                _orderSummaryState.update {
                    it.copy(
                        isLoading = false,
                        order = order,
                    )
                }
            } else {
                _orderSummaryState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Unable to load order details.",
                    )
                }
            }
        }
    }
}
