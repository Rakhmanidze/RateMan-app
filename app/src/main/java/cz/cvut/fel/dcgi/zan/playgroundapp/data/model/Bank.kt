package cz.cvut.fel.dcgi.zan.playgroundapp.data.model

data class Bank(
    private val name: String,
    private val baseCurrency: CurrencyCode,
    private val rates: List<CurrencyRate>,
    private val nearestBranchAddress: Address,
    private val phoneNumber: String
) {
    fun getName(): String {
        return name;
    }

    fun getBaseCurrency(): CurrencyCode {
        return baseCurrency
    }

    fun getRate(currencyCode: CurrencyCode): CurrencyRate? {
        return rates.find { it.getForeignCurrency() == currencyCode }
    }

    fun getNearestBranchAddress(): Address {
        return nearestBranchAddress
    }

    fun getPhoneNumber(): String {
        return phoneNumber
    }
}