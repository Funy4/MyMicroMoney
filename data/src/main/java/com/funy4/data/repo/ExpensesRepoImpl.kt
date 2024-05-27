package com.funy4.data.repo

import com.funy4.data.db.dao.ExpensesDao
import com.funy4.data.db.dao.TransactionDao
import com.funy4.data.toEntity
import com.funy4.data.toModel
import com.funy4.domain.model.ExpenseModel
import com.funy4.domain.repo.ExpensesRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class ExpensesRepoImpl @Inject constructor(
    private val expensesDao: ExpensesDao
) : ExpensesRepo {

    override suspend fun create(model: ExpenseModel) {
        expensesDao.insert(model.toEntity())
    }

    override suspend fun update(model: ExpenseModel) {
        expensesDao.update(model.toEntity())
    }

    override suspend fun delete(id: UUID) {
        get(id)?.let {
            expensesDao.delete(it.toEntity())
        }
    }

    override suspend fun get(id: UUID): ExpenseModel? = expensesDao.getWithTransaction(id)?.let {
        it.expense.toModel(it.transactions.sumOf { it.cost })
    }

    override fun getAllFlow(): Flow<List<ExpenseModel>> {
        return expensesDao.getAllWithTransactions().map { list ->
            list.map {
                it.expense.toModel(it.transactions.sumOf { it.cost })
            }
        }
    }
}