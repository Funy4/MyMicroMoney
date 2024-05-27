package com.funy4.domain.usecase.cash

import com.funy4.domain.repo.CashRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllCashUseCase @Inject constructor(
    private val cashRepo: CashRepo,
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) { cashRepo.getAllFlow() }
}