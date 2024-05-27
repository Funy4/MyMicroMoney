package com.funy4.domain.usecase.income

import com.funy4.domain.repo.IncomeRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllIncomeUseCase @Inject constructor(
    private val incomeRepo: IncomeRepo
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        incomeRepo.getAllFlow()
    }
}