package com.funy4.domain.usecase.income

import com.funy4.domain.repo.IncomeRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject


class DeleteIncomeUseCase @Inject constructor(
    private val incomeRepo: IncomeRepo
) {
    suspend operator fun invoke(id: UUID) = withContext(Dispatchers.IO) {
        incomeRepo.delete(id)
    }
}