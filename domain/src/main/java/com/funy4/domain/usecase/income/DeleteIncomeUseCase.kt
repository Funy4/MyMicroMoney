package com.funy4.domain.usecase.income

import com.funy4.domain.repo.IncomeRepo
import java.util.*
import javax.inject.Inject


class DeleteIncomeUseCase @Inject constructor(
    private val incomeRepo: IncomeRepo
) {
    suspend operator fun invoke(id: UUID) {
        incomeRepo.delete(id)
    }
}