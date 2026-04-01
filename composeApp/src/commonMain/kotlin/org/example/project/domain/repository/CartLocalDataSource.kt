package org.example.project.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.model.Product

interface CartLocalDataSource {
    suspend fun add(product: Product): Long

    suspend fun update(product: Product)

    suspend fun remove(product: Product)

    fun observeCartItems(): Flow<List<Product>>

    suspend fun getCartItem(id: String): Product?

    suspend fun clear()
}
