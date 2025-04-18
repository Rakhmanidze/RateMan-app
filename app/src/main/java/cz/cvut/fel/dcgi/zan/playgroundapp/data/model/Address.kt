package cz.cvut.fel.dcgi.zan.playgroundapp.data.model

data class Address(
    private val street: String,
    private val buildingNumber: String,
    private val city: String,
    private val postalCode: String,
    private val country: String
) {
    fun getStreet(): String {
        return street
    }

    fun getBuildingNumber(): String {
        return buildingNumber
    }

    fun getCity(): String {
        return city
    }

    fun getPostalCode(): String {
        return postalCode
    }

    fun getCountry(): String {
        return country
    }
}