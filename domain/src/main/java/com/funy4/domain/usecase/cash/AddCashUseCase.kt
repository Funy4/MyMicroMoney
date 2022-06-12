package com.funy4.domain.usecase.cash

import com.funy4.domain.model.CashModel
import com.funy4.domain.repo.CashRepo
import javax.inject.Inject


class AddCashUseCase @Inject constructor(
    private val cashRepo: CashRepo
) {
    suspend operator fun invoke(cashModel: CashModel) {
        cashRepo.create(cashModel)
    }
}