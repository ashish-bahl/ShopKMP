package org.example.project.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class CartItemEntity(
    @PrimaryKey val id: String,
    val name: String,
    val quantity: Int,
    val description: String,
    val costPrice: Double,
    val sellingPrice: Double,
    val imageUrl: String,
)
