package org.example.project.presentation.order.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.model.Order
import org.example.project.domain.usecase.cart.ClearCartUseCase
import org.example.project.domain.usecase.cart.ObserveCartItemsUseCase
import org.example.project.domain.usecase.order.AddToOrderUseCase
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CheckoutViewModel (
    private val observeCartItemsUseCase: ObserveCartItemsUseCase,
    private val addToOrderUseCase: AddToOrderUseCase,
    private val clearCartUseCase: ClearCartUseCase
) : ViewModel() {

    private val _checkoutState = MutableStateFlow(CheckoutState())
    val checkoutState = _checkoutState.asStateFlow()

    private val _checkoutEffect = MutableSharedFlow<CheckoutEffect>()
    val checkoutEffect = _checkoutEffect.asSharedFlow()

    @OptIn(ExperimentalUuidApi::class)
    fun placeOrder() {
        if (!validateInputs(
                _checkoutState.value.name, _checkoutState.value.address, _checkoutState.value.number
            ) || _checkoutState.value.cartItems.isEmpty()
        ) return

        viewModelScope.launch {
            val state = _checkoutState.value
            val uuid = Uuid.generateV7()
            if(addToOrderUseCase.invoke(
                    Order(
                        id = uuid.toString(),
                        cartItems = state.cartItems,
                        totalAmount = state.totalAmount,
                        totalQuantity = state.totalQuantity,
                        name = state.name,
                        address = state.address,
                        number = state.number,
                    )
            ) != -1L){
                clearCartUseCase.invoke()
                _checkoutEffect.emit(CheckoutEffect.NavigateToOrderSummary(uuid))
            }
        }

    }

    private fun validateName(name: String): String? {
        if (name.isBlank()) return "Name can not be empty"
        return null
    }

    private fun validateAddress(address: String): String? {
        if (address.isBlank()) return "Address can not be empty"
        return null
    }

    private fun validateNumber(number: String): String? {
        if (number.isBlank()) return "Number can not be empty"
        if (number.length != 10 || number.any { !it.isDigit() }) return "Please enter a valid number"
        return null
    }

    private fun validateInputs(name: String, address: String, number: String): Boolean {
        val nameError = validateName(name)
        val addressError = validateAddress(address)
        val numberError = validateNumber(number)
        val isValid = nameError == null && addressError == null && numberError == null

        // set all errors in a single state update
        _checkoutState.update {
            it.copy(
                nameErrorMessage = nameError ?: "",
                addressErrorMessage = addressError ?: "",
                numberErrorMessage = numberError ?: "",
            )
        }

        return isValid
    }

    fun getCartProducts() {
        viewModelScope.launch {
            observeCartItemsUseCase.invoke().collect { items ->
                _checkoutState.update { state ->
                    state.copy(
                        cartItems = items,
                        totalAmount = items.sumOf { it.sellingPrice * it.quantity },
                        totalQuantity = items.sumOf { it.quantity }
                    )
                }
            }
        }
    }

    fun updateName(name: String) {
        _checkoutState.update { it.copy(name = name) }
    }

    fun updateAddress(address: String) {
        _checkoutState.update { it.copy(address = address) }
    }

    fun updateNumber(number: String) {
        _checkoutState.update { it.copy(number = number) }
    }
}
