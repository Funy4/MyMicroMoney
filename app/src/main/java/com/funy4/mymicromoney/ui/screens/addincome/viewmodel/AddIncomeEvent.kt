package com.funy4.mymicromoney.ui.screens.addincome.viewmodel

import com.funy4.domain.model.CashModel
import com.funy4.domain.model.IncomeModel
import com.funy4.mymicromoney.ui.screens.addexpense.viewmodel.AddExpenseEvent
import java.time.LocalDate
import java.util.UUID

sealed interface AddIncomeEvent {
    data class InitBaseIncome(val id: UUID): AddIncomeEvent

    data class OnTransactionNameChange(val transactionName: String) : AddIncomeEvent
    data class OnMoneyChange(val money: String) : AddIncomeEvent
    object OnChangeIncomeClick : AddIncomeEvent
    object OnChangeCashClick : AddIncomeEvent
    object OnDismissChangeIncome : AddIncomeEvent
    object OnDismissChangeCash : AddIncomeEvent
    data class OnIncomeChange(val income: IncomeModel) : AddIncomeEvent
    data class OnCashChange(val cashModel: CashModel) : AddIncomeEvent

    data class OnDateChange(val date: LocalDate) : AddIncomeEvent
    object OnSelectDateClick : AddIncomeEvent
    object OnDismissSelectDate : AddIncomeEvent

    data class OnSaveIncomeClick(val name: String, val cost: Double) : AddIncomeEvent
    object OnBack : AddIncomeEvent
}