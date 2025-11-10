package com.currency.rateman.api

import com.currency.rateman.provider.domain.model.ProviderType

object ProviderTypeClassifier {
    fun determineProviderType(bankName: String): ProviderType {
        return if (ProviderConstants.KNOWN_BANKS.any { bankName.contains(it, ignoreCase = true) }) {
            ProviderType.BANK
        } else {
            ProviderType.EXCHANGE
        }
    }
}