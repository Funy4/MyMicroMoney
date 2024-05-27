package com.funy4.data.repo

import com.funy4.data.db.dao.CashDao
import com.funy4.data.toEntity
import com.funy4.data.toModel
import com.funy4.domain.model.CashModel
import com.funy4.domain.repo.CashRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class CashRepoImpl @Inject constructor(
    private val cashDao: CashDao
) : CashRepo {
    override suspend fun create(model: CashModel) {
        cashDao.insert(model.toEntity())
    }

    override suspend fun update(model: CashModel) {
        cashDao.update(model.toEntity())
    }

    override suspend fun delete(id: UUID) {
        val cash = get(id) ?: return
        cashDao.delete(cash.toEntity())
    }

    override suspend fun get(id: UUID) = cashDao.get(id)?.toModel()

    override suspend fun getCashSum(): Flow<Double> = cashDao.getCashSum()

    override fun getAllFlow() = cashDao.getAll().map { list -> list.map { it.toModel() } }
}