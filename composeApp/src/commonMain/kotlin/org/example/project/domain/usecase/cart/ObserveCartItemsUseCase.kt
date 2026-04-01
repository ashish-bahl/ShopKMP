package org.example.project.domain.usecase.cart

import org.example.project.domain.repository.CartLocalDataSource

class ObserveCartItemsUseCase(
    private val cartLocalDataSource: CartLocalDataSource,
) {
    operator fun invoke() = cartLocalDataSource.observeCartItems()
}
