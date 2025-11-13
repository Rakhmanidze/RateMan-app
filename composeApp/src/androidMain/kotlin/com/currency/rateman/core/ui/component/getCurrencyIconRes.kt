package com.currency.rateman.core.ui.component

import androidx.annotation.DrawableRes
import com.currency.rateman.R
import com.currency.rateman.core.domain.model.CurrencyCode

@DrawableRes
fun getCurrencyIconRes(currency: CurrencyCode): Int {
    val specialCases = mapOf(
        CurrencyCode.TRY to R.drawable.tryy
    )
    specialCases[currency]?.let { return it }

    val resourceName = currency.name.lowercase()
    return try {
        val field = R.drawable::class.java.getField(resourceName)
        field.getInt(null)
    } catch (_: Exception) {
        R.drawable.usd
    }
}