package org.example.project.data.repository

import kotlinx.coroutines.flow.map
import org.example.project.data.local.database.StorefrontDatabase
import org.example.project.data.local.mapper.toDomainModel
import org.example.project.data.local.mapper.toLocalEntity
import org.example.project.domain.model.Product
import org.example.project.domain.repository.CartRepository

class DefaultCartRepository(
    private val storefrontDatabase: StorefrontDatabase,
) : CartRepository {
    private val dao = storefrontDatabase.storefrontDao()

    override suspend fun add(product: Product) = dao.addToCart(product.toLocalEntity())

    override suspend fun update(product: Product) = dao.updateCartItem(product.toLocalEntity())

    override suspend fun remove(product: Product) = dao.removeFromCart(product.toLocalEntity())

    override fun observeCartItems() = dao.observeCartItems()
        .map { items -> items.map { it.toDomainModel() } }

    override suspend fun getCartItem(id: String) = dao.getCartItem(id)?.toDomainModel()

    override suspend fun clear() = dao.clearCart()
}