package com.currency.rateman.core.ui.component

import com.currency.rateman.core.domain.model.CurrencyCode
import org.jetbrains.compose.resources.DrawableResource
import rateman.composeapp.generated.resources.Res
import rateman.composeapp.generated.resources.tryy
import rateman.composeapp.generated.resources.usd

fun getCurrencyIcon(currency: CurrencyCode): DrawableResource {
    return when (currency) {
        CurrencyCode.TRY -> Res.drawable.tryy
        else -> Res.drawable.usd
    }
}
