package com.currency.rateman.api.kurzyCz

import retrofit2.http.GET

interface RatesAPIService {
    @GET("/json/meny/b%5B-1%5D.json")
    suspend fun getExchangeRates(): List<ProviderAPI>
}