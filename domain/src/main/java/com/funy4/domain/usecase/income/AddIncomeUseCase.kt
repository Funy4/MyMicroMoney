package com.funy4.domain.usecase.income

import com.funy4.domain.model.IncomeModel
import com.funy4.domain.model.TransactionModel
import com.funy4.domain.repo.IncomeRepo
import javax.inject.Inject


class AddIncomeUseCase @Inject constructor(
    private val incomeRepo: IncomeRepo
) {
    suspend operator fun invoke(model: IncomeModel) {
        incomeRepo.create(model)
    }
}