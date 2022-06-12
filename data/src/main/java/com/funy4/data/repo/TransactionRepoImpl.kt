package com.funy4.data.repo

import com.funy4.data.db.dao.TransactionDao
import com.funy4.data.toEntity
import com.funy4.data.toModel
import com.funy4.domain.model.TransactionModel
import com.funy4.domain.model.type.TransactionType
import com.funy4.domain.repo.TransactionRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

open class TransactionRepoImpl @Inject constructor(
    private val transactionDao: TransactionDao
) : TransactionRepo {
    override suspend fun create(model: TransactionModel) {
        transactionDao.insert(model.toEntity())
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

}