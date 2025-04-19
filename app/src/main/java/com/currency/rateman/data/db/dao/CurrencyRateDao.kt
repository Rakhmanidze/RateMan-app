package com.currency.rateman.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.currency.rateman.data.db.entity.CurrencyRateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyRateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRate(rate: CurrencyRateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRates(rates: List<CurrencyRateEntity>)

    @Query("SELECT * FROM currency_rates WHERE providerId = :providerId")
    fun getRatesForProvider(providerId: Long): Flow<List<CurrencyRateEntity>>

    @Query("SELECT * FROM currency_rates WHERE providerId = :providerId AND foreignCurrency = :currencyCode")
    suspend fun getSpecificRate(providerId: Long, currencyCode: String): CurrencyRateEntity?

    @Query("SELECT * FROM currency_rates WHERE foreignCurrency = :currencyCode")
    fun getRatesByCurrency(currencyCode: String): Flow<List<CurrencyRateEntity>>

    @Update
    suspend fun updateRate(rate: CurrencyRateEntity)

    @Query("UPDATE currency_rates SET buyRate = :buyRate, sellRate = :sellRate WHERE id = :rateId")
    suspend fun updateRateValues(rateId: Long, buyRate: Double, sellRate: Double)

    @Delete
    suspend fun deleteRate(rate: CurrencyRateEntity)

    @Query("DELETE FROM currency_rates WHERE providerId = :providerId")
    suspend fun deleteRatesForProvider(providerId: Long)
}