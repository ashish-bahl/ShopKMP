package org.example.project.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.example.project.data.local.entity.CartItemEntity
import org.example.project.data.local.entity.OrderEntity

@Dao
interface StorefrontDao {
    @Insert(onConflict = REPLACE)
    suspend fun addToCart(cartItem: CartItemEntity): Long

    @Delete
    suspend fun removeFromCart(cartItem: CartItemEntity)

    @Update(onConflict = REPLACE)
    suspend fun updateCartItem(cartItem: CartItemEntity)

    @Query("SELECT * FROM cart")
    fun observeCartItems(): Flow<List<CartItemEntity>>

    @Query("SELECT * FROM cart WHERE id = :id")
    suspend fun getCartItem(id: String): CartItemEntity?

    @Query("DELETE FROM cart")
    suspend fun clearCart()

    @Insert(onConflict = REPLACE)
    suspend fun placeOrder(order: OrderEntity): Long

    @Query("SELECT * FROM orders")
    fun observeOrders(): Flow<List<OrderEntity>>

    @Query("SELECT * FROM orders WHERE id = :id")
    suspend fun getOrder(id: String): OrderEntity?
}
