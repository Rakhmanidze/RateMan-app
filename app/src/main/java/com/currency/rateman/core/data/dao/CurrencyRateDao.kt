package com.currency.rateman.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.currency.rateman.core.data.entity.CurrencyRateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyRateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRate(rate: CurrencyRateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRates(rates: List<CurrencyRateEntity>)

    @Query("SELECT * FROM currency_rates WHERE providerId = :providerId")
    fun getRatesForProvider(providerId: Long): Flow<List<CurrencyRateEntity>>
}