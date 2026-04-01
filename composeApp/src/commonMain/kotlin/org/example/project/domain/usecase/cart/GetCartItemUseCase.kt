package org.example.project.domain.usecase.cart

import org.example.project.domain.repository.CartLocalDataSource

class GetCartItemUseCase(
    private val cartLocalDataSource: CartLocalDataSource,
) {
    suspend operator fun invoke(id: String) = cartLocalDataSource.getCartItem(id)
}
