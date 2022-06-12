package com.funy4.domain.usecase.expenses

import com.funy4.domain.model.ExpenseModel
import com.funy4.domain.repo.ExpensesRepo
import java.awt.Color
import java.util.*
import javax.inject.Inject


class AddExpensesUseCase @Inject constructor(
    private val expensesRepo: ExpensesRepo
) {
    suspend operator fun invoke(name: String, color: Int, iconId: Int) {
        expensesRepo.create(
            ExpenseModel(
                id = UUID.randomUUID(),
                name = name,
                color = color,
                iconId = iconId,
                money = 0.0,
            )
        )
    }
}