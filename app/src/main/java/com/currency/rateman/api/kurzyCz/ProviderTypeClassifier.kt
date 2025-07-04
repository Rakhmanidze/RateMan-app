package com.currency.rateman.api.kurzyCz

import com.currency.rateman.data.model.enums.ProviderType
import com.currency.rateman.utils.ProviderConstants

object ProviderTypeClassifier {
    fun determineProviderType(bankName: String): ProviderType {
        return if (ProviderConstants.KNOWN_BANKS.any { bankName.contains(it, ignoreCase = true) }) {
            ProviderType.BANK
        } else {
            ProviderType.EXCHANGE
        }
    }
}