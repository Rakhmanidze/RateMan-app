package com.currency.rateman.provider.utils

object ProviderConstants {
    val EXCLUDED_PROVIDERS = listOf(
        "AKCENTA CZ",
        "Česká národní banka",
        "Citfin",
        "Exchange",
        "Poštovní spořitelna",
        "Prepocet EURa",
        "RoklenFX",
        "Turecká centrální banka"
    )

    val KNOWN_BANKS = listOf(
        "Air Bank",
        "Česká Spořitelna",  //Czech Savings Bank for non-czech languages
        "ČSOB", //CSOB for non-czech languages
        "Fio banka", //Fio Bank for non-czech languages
        "Komerční banka", //Commercial Bank for non-czech languages
        "Max banka", //Max Bank for non-czech languages
        "mBank",
        "Moneta Money Bank", //is official name.
        "MONETA",
        "Oberbank",
        "Raiffeisenbank", //Raiffeisen Bank for non-czech languages
        "Trinity Bank",
        "UniCredit Bank"
    )

    object Scraped_providers {
        const val ALFA_PRAGUE = "Alfa Prague"
        const val AURA_AKTIV = "Aura Aktiv"
        const val CERNA_RUZE_EXCHANGE = "Černá Růže Exchange"
        const val EURO_CHANGE = "Euro Change"
        const val EXCHANGE8 = "Exchange8"
        const val JINDRISSKA_EXCHANGE = "Jindřišská Exchange"
        const val ROYAL_EXCHANGE = "Royal Exchange"
        const val TOP_EXCHANGE = "Top Exchange"
    }

    object Urls {
        const val ALFA_PRAGUE = "https://www.alfaprague.cz/web2/"
        const val AURA_AKTIV = "https://smenarna-praha-1.cz/en/"
        const val CERNA_RUZE_EXCHANGE = "https://www.cernaruze-exchange.cz/"
        const val EURO_CHANGE = "https://www.eurochange.cz/"
        const val EXCHANGE8 = "https://www.exchange8.cz/"
        const val JINDRISSKA_EXCHANGE = "https://jindrisska-exchange.cz/"
        const val ROYAL_EXCHANGE = "https://www.royalexchange.cz/"
        const val TOP_EXCHANGE = "https://top-exchange.cz/"
    }
}