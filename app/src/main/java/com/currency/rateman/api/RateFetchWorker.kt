package com.currency.rateman.api

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.currency.rateman.data.repository.RateProviderRepository
import com.currency.rateman.utils.ProviderConstants.EXCLUDED_PROVIDERS
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RateFetchWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KoinComponent {

    private val rateProviderRepository: RateProviderRepository by inject()

    override suspend fun doWork(): Result {
        Log.d("RateFetchWorker", "Starting API fetch")
        return try {
            Log.d("RateFetchWorker", "Trying to fetch rates")
            fetchAndStoreRates()
            Result.success()
        } catch (e: Exception) {
            Log.e("RateFetchWorker", "Error fetching rates: ${e.message}", e)
            Result.failure()
        }
    }

    private suspend fun fetchAndStoreRates() {
        try {
            val response = APIClient.ratesAPIService.getExchangeRates()
            Log.d("RatesViewModel", "API Response received, size: ${response.size}")

            val filteredProviders = response.filter { provider ->
                !EXCLUDED_PROVIDERS.contains(provider.banka)
            }
            rateProviderRepository.insertApiProviders(filteredProviders)
        } catch (e: Exception) {
            Log.e("RatesViewModel", "API Error: ${e.message}", e)
            Log.e("RatesViewModel", "Stack trace: ${e.stackTraceToString()}")
        }
    }
}