package com.currency.rateman.api

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.currency.rateman.api.APIClient
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

    }
}