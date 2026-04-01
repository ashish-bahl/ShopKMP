package org.example.project.data.repository

import kotlinx.coroutines.flow.map
import org.example.project.data.local.dao.StorefrontDao
import org.example.project.data.local.mapper.toDomainModel
import org.example.project.data.local.mapper.toLocalEntity
import org.example.project.domain.model.Product
import org.example.project.domain.repository.CartRepository

class DefaultCartRepository(
    private val storefrontDao: StorefrontDao,
) : CartRepository {
    override suspend fun add(product: Product) = storefrontDao.addToCart(product.toLocalEntity())

    override suspend fun update(product: Product) = storefrontDao.updateCartItem(product.toLocalEntity())

    override suspend fun remove(product: Product) = storefrontDao.removeFromCart(product.toLocalEntity())

    override fun observeCartItems() = storefrontDao.observeCartItems()
        .map { items -> items.map { it.toDomainModel() } }

    override suspend fun getCartItem(id: String) = storefrontDao.getCartItem(id)?.toDomainModel()

    override suspend fun clear() = storefrontDao.clearCart()
}
