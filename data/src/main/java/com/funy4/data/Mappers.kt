package com.funy4.data

import com.funy4.data.db.entity.CashEntity
import com.funy4.data.db.entity.ExpensesEntity
import com.funy4.data.db.entity.IncomeEntity
import com.funy4.data.db.entity.TransactionEntity
import com.funy4.domain.model.CashModel
import com.funy4.domain.model.ExpenseModel
import com.funy4.domain.model.IncomeModel
import com.funy4.domain.model.TransactionModel

fun CashEntity.toModel() = CashModel(
    id = id,
    name = name,
    icon = icon,
    money = money,
)

fun CashModel.toEntity() = CashEntity(
    id = id,
    name = name,
    icon = icon,
    money = money,)

fun TransactionEntity.toModel() = TransactionModel(
    id = id,
    name = name,
    cost = cost,
    cashId = cashId,
    transactionType = transactionType,
    date = date
)

fun TransactionModel.toEntity() = TransactionEntity(
    id = id,
    name = name,
    cost = cost,
    cashId = cashId,
    transactionType = transactionType,
    date = date
)

fun ExpensesEntity.toModel() = ExpenseModel(
    id = id,
    name = name,
    color = color,
    iconId = icon,
    money = money
)

fun ExpenseModel.toEntity() = ExpensesEntity(
    id = id,
    name = name,
    color = color,
    icon = iconId,
    money = money
)

fun IncomeEntity.toModel() = IncomeModel(
    id = id,
    name = name,
    color = color,
    iconId = icon,
    money = money,
)

fun IncomeModel.toEntity() = IncomeEntity(
    id = id,
    name = name,
    color = color,
    icon = iconId,
    money = money
)
