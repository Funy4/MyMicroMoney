package com.funy4.domain.usecase.cash

import com.funy4.domain.repo.CashRepo
import javax.inject.Inject

class GetAllCashUseCase @Inject constructor(
    private val cashRepo: CashRepo,
){
    operator fun invoke() = cashRepo.getAllFlow()
}