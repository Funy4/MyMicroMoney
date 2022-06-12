package com.funy4.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.funy4.data.db.entity.IncomeEntity
import com.funy4.data.model.IncomeWithTransactionEntityModel
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface IncomeDao: BaseDao<IncomeEntity> {
    @Query("select * from incomes where id = :id")
    suspend fun get(id: UUID): IncomeEntity?

    @Query("select * from incomes")
    fun getAll(): Flow<List<IncomeEntity>>

    @Transaction
    @Query("select * from incomes")
    fun getIncomesWithTransactions(): Flow<List<IncomeWithTransactionEntityModel>>
}