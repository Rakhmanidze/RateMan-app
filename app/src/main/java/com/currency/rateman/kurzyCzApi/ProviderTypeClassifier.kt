package com.currency.rateman.kurzyCzApi

import com.currency.rateman.data.model.enums.ProviderType
import com.currency.rateman.utils.ProviderConstants.KNOWN_BANKS

object ProviderTypeClassifier {
    fun determineProviderType(bankName: String): ProviderType {
        return if (KNOWN_BANKS.any { bankName.contains(it, ignoreCase = true) }) {
            ProviderType.BANK
        } else {
            ProviderType.EXCHANGE
        }
    }
}