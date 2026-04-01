package org.example.project.data.local.converter

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import org.example.project.data.local.entity.CartItemEntity

class StorefrontConverters {
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    @TypeConverter
    fun fromCartItems(value: List<CartItemEntity>?): String? {
        return value?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun toCartItems(value: String?): List<CartItemEntity>? {
        return value?.let { json.decodeFromString(it) }
    }
}
