package com.currency.rateman.api.kurzyCz

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.currency.rateman.provider.domain.repository.ProviderRepository
import com.currency.rateman.provider.utils.ProviderConstants
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RateFetchWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KoinComponent {
    private val providerRepository: ProviderRepository by inject()

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
            val response: List<ProviderAPI> = APIClient.getRates("json/meny/b%5B-1%5D.json")
            Log.d("RatesViewModel", "API Response received, size: ${response.size}")

            val filteredProviders = response
                .filter { provider ->
                !ProviderConstants.EXCLUDED_PROVIDERS.contains(provider.banka)
                }
                .map { provider ->
                    if (provider.banka == "Exchange VIP") {
                        provider.copy(banka = "Směnárna Štefánikova")
                    } else {
                        provider
                    }
                }
            providerRepository.insertApiProviders(filteredProviders)
        } catch (e: Exception) {
            Log.e("RatesViewModel", "API Error: ${e.message}", e)
            Log.e("RatesViewModel", "Stack trace: ${e.stackTraceToString()}")
        }
    }
}