package org.example.project

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.io.File
import org.example.project.data.local.database.STOREFRONT_DATABASE_NAME
import org.example.project.data.local.database.StorefrontDatabase

fun getStorefrontDatabase(): StorefrontDatabase {
    val dbFile = File(
        System.getProperty("java.io.tmpdir"),
        STOREFRONT_DATABASE_NAME,
    )

    return Room.databaseBuilder<StorefrontDatabase>(
        name = dbFile.absolutePath,
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}
