package org.example.project.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.model.Order

interface OrderRepository {
    suspend fun placeOrder(order: Order): Long

    fun observeOrders(): Flow<List<Order>>

    suspend fun getOrder(id: String): Order?
}
