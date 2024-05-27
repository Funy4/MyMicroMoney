package com.funy4.data.repo

import com.funy4.data.db.dao.IncomeDao
import com.funy4.data.db.dao.TransactionDao
import com.funy4.data.toEntity
import com.funy4.data.toModel
import com.funy4.domain.model.IncomeModel
import com.funy4.domain.model.TransactionModel
import com.funy4.domain.model.type.TransactionType
import com.funy4.domain.repo.IncomeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class IncomeRepoImpl @Inject constructor(
    private val incomeDao: IncomeDao
) : IncomeRepo {
    override suspend fun create(model: IncomeModel) {
        incomeDao.insert(model.toEntity())
    }

    override suspend fun update(model: IncomeModel) {
        incomeDao.update(model.toEntity())
    }

    override suspend fun delete(id: UUID) {
        get(id)?.let {
            incomeDao.delete(it.toEntity())
        }
    }

    override suspend fun get(id: UUID): IncomeModel? = incomeDao.get(id)?.let{
        it.income.toModel(it.transactions.sumOf { it.cost })
    }

    override fun getAllFlow(): Flow<List<IncomeModel>> =
        incomeDao.getIncomesWithTransactions().map { list -> list.map { it.income.toModel(it.transactions.sumOf { it.cost }) } }

}