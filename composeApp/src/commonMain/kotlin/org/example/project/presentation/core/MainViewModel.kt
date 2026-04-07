package org.example.project.presentation.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.domain.usecase.cart.ObserveCartItemsUseCase

class MainViewModel (
    private val observeCartItemsUseCase: ObserveCartItemsUseCase
): ViewModel() {
    private val _cartItemCountFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    val cartItemCountFlow: StateFlow<Int> = _cartItemCountFlow.asStateFlow()

    init {
        observeCartItemCount()
    }

    private fun observeCartItemCount() {
        viewModelScope.launch {
            observeCartItemsUseCase().collect { items ->
                _cartItemCountFlow.value = items.sumOf { it.quantity }
            }
        }
    }
}