package org.example.project.data.repository

import kotlinx.coroutines.flow.map
import org.example.project.data.local.database.StorefrontDatabase
import org.example.project.data.local.mapper.toDomainModel
import org.example.project.data.local.mapper.toLocalEntity
import org.example.project.domain.model.Product
import org.example.project.domain.repository.CartLocalDataSource

class CartLocalDataSourceImpl(
    private val storefrontDatabase: StorefrontDatabase,
) : CartLocalDataSource {
    override suspend fun add(product: Product) = storefrontDatabase.storefrontDao().addToCart(product.toLocalEntity())

    override suspend fun update(product: Product) = storefrontDatabase.storefrontDao().updateCartItem(product.toLocalEntity())

    override suspend fun remove(product: Product) = storefrontDatabase.storefrontDao().removeFromCart(product.toLocalEntity())

    override fun observeCartItems() = storefrontDatabase.storefrontDao().observeCartItems()
        .map { items -> items.map { it.toDomainModel() } }

    override suspend fun getCartItem(id: String) = storefrontDatabase.storefrontDao().getCartItem(id)?.toDomainModel()

    override suspend fun clear() = storefrontDatabase.storefrontDao().clearCart()
}
