package org.example.project.presentation.utils

import java.util.Locale

actual fun formatAmount(amount: Double): String {
    return if (amount == amount.toLong().toDouble()) {
        amount.toLong().toString()
    } else {
        String.format(Locale.getDefault(), "%.2f", amount)
    }
}
