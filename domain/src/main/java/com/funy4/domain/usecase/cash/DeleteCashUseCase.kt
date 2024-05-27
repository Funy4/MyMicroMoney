package com.funy4.domain.usecase.cash

import com.funy4.domain.repo.CashRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject


class DeleteCashUseCase @Inject constructor(
    private val cashRepo: CashRepo
) {
    suspend operator fun invoke(id: UUID) = withContext(Dispatchers.IO) {
        cashRepo.delete(id)
    }
}