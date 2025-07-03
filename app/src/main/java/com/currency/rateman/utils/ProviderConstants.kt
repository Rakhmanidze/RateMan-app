package com.currency.rateman.utils

object ProviderConstants {
    val EXCLUDED_PROVIDERS = listOf(
        "Turecká centrální banka",
        "Poštovní spořitelna",
        "Prepocet EURa",
        "Exchange",
        "Česká národní banka",
        "Citfin",
        "AKCENTA CZ",
        "RoklenFX"
    )

    val KNOWN_BANKS = listOf(
        "Komerční Banka",
        "Česká Spořitelna",
        "Moneta Money Bank",
        "Raiffeisenbank",
        "Unicredit Bank",
        "Fio Banka",
        "Air Bank",
        "MONETA",
        "Max banka",
        "ČSOB",
        "mBank",
        "Trinity Bank",
        "Oberbank AG"
    )

    object Urls {
        const val ALFA_PRAGUE = "https://www.alfaprague.cz/web2/"
        const val JINDRISSKA_EXCHANGE = "https://jindrisska-exchange.cz/"
        const val TOP_EXCHANGE = "https://top-exchange.cz/"
    }
}