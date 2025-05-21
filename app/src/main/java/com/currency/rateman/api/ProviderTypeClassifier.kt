package com.currency.rateman.api

import com.currency.rateman.data.model.enums.ProviderType

object ProviderTypeClassifier {
    private val knownBanks = listOf(
        "Komerční Banka",
        "Česká Spořitelna",
        "Moneta Money Bank",
        "Raiffeisenbank",
        "Unicredit Bank",
        "Fio Banka",
        "Air Bank"
    )

    fun determineProviderType(bankName: String): ProviderType {
        return if (knownBanks.any { bankName.contains(it, ignoreCase = true) }) {
            ProviderType.BANK
        } else {
            ProviderType.EXCHANGE
        }
    }
}