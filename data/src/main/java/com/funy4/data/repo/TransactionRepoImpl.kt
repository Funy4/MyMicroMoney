package com.funy4.data.repo

import android.os.Build
import androidx.annotation.RequiresApi
import com.funy4.data.db.dao.CashDao
import com.funy4.data.db.dao.ExpenseIncomeTransactionalDao
import com.funy4.data.db.dao.TransactionDao
import com.funy4.data.toEntity
import com.funy4.data.toModel
import com.funy4.domain.model.ExpenseWithTransactionsModel
import com.funy4.domain.model.IncomeWithTransactionsModel
import com.funy4.domain.model.TransactionModel
import com.funy4.domain.model.TransactionShortModel
import com.funy4.domain.repo.TransactionRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

open class TransactionRepoImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val expenseIncomeTransactionalDao: ExpenseIncomeTransactionalDao,
    private val cashDao: CashDao
) : TransactionRepo {
    override suspend fun create(model: TransactionModel) {
        transactionDao.insert(model.toEntity())
    }

    override suspend fun createExpenseTransaction(transaction: TransactionModel, expenseId: UUID) {
        expenseIncomeTransactionalDao.insertExpenseTransaction(
            transactionDao, cashDao, expenseId, transaction.toEntity()
        )
    }

    override suspend fun createIncomeTransaction(transaction: TransactionModel, incomeId: UUID) {
        expenseIncomeTransactionalDao.insertIncomeTransaction(
            transactionDao, cashDao, incomeId, transaction.toEntity()
        )
    }


    override suspend fun update(model: TransactionModel) {
        transactionDao.update(model.toEntity())
    }

    override suspend fun delete(id: UUID) {
        val transaction = get(id) ?: return
        transactionDao.delete(transaction.toEntity())
    }

    override suspend fun get(id: UUID): TransactionModel? = transactionDao.get(id)?.toModel()

    override fun getAllFlow(): Flow<List<TransactionModel>> =
        transactionDao.getAll().map { list -> list.map { it.toModel() } }

    override fun getAllExpenses(): Flow<List<ExpenseWithTransactionsModel>> =
        transactionDao.getAllExpenses().map {
            it.map {
                ExpenseWithTransactionsModel(
                    it.expense.toModel(it.transactions.sumOf { it.cost }),
                    it.transactions.map { it.toModel() })
            }
        }

    override fun getAllIncome(): Flow<List<IncomeWithTransactionsModel>> =
        transactionDao.getAllIncome().map {
            it.map {
                IncomeWithTransactionsModel(
                    it.income.toModel(it.transactions.sumOf { it.cost }),
                    it.transactions.map { it.toModel() })
            }
        }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun getAllShort(): List<TransactionShortModel> {
        return buildList {
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 1, 0, 0), -119.41))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 2, 0, 0), 194.1))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 3, 0, 0), -15.3))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 4, 0, 0), 40.11))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 5, 0, 0), 10.89))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 6, 0, 0), -41.13))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 7, 0, 0), -164.0))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 8, 0, 0), -172.2))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 9, 0, 0), 7.54))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 10, 0, 0), 80.67))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 11, 0, 0), -128.2))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 12, 0, 0), 5000.0))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 13, 0, 0), 40.2))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 14, 0, 0), -80.1))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 15, 0, 0), 143.49))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 16, 0, 0), 1720.46))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 17, 0, 0), -176.33))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 18, 0, 0), -66.61))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 19, 0, 0), 160.05))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 20, 0, 0), 46.76))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 21, 0, 0), -184.23))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 22, 0, 0), -30.96))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 23, 0, 0), 133.19))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 24, 0, 0), 140.38))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 25, 0, 0), 84.84))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 26, 0, 0), 102.87))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 27, 0, 0), 193.36))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 28, 0, 0), -3.26))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 29, 0, 0), 126.58))
            add(TransactionShortModel(LocalDateTime.of(2022, 6, 30, 0, 0), -102.06))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 1, 0, 0), 172.89))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 2, 0, 0), -163.37))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 3, 0, 0), -14.22))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 4, 0, 0), 192.53))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 5, 0, 0), 159.55))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 6, 0, 0), 1990.41))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 7, 0, 0), 78.96))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 8, 0, 0), -193.36))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 9, 0, 0), -55.43))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 10, 0, 0), 172.56))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 11, 0, 0), 163.91))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 12, 0, 0), 5000.0))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 13, 0, 0), -18.71))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 14, 0, 0), 72.02))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 15, 0, 0), 350.34))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 16, 0, 0), 71.73))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 17, 0, 0), -59.91))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 18, 0, 0), -135.15))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 19, 0, 0), 104.05))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 20, 0, 0), -34.83))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 21, 0, 0), -181.68))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 22, 0, 0), 195.09))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 23, 0, 0), 108.17))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 24, 0, 0), -150.83))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 25, 0, 0), -0.45))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 26, 0, 0), 85.77))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 27, 0, 0), -171.68))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 28, 0, 0), 113.27))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 29, 0, 0), -166.9))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 30, 0, 0), 81.64))
            add(TransactionShortModel(LocalDateTime.of(2022, 7, 31, 0, 0), -125.91))
        }
    }
}