package com.funy4.data.db.dao

import androidx.room.*
import com.funy4.data.db.entity.CashEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface CashDao: BaseDao<CashEntity> {
    @Query("select * from cash")
    fun getAll(): Flow<List<CashEntity>>

    @Query("select * from cash where id = :id")
    suspend fun get(id: UUID): CashEntity?
}