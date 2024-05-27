package com.funy4.mymicromoney

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.funy4.domain.model.*
import com.funy4.domain.model.type.TransactionType
import java.util.*

class Mocks {
    companion object {
        val expensesList = mutableListOf(
            ExpenseModel(
                id = UUID.fromString("bbe129d3-78e7-40cd-a3b6-f251a76e5a0f"),
                name = "First",
                money = 15.0,
                iconId = R.drawable.ic_9,
                color = Color.Gray.toArgb(),
            ),
            ExpenseModel(
                id = UUID.fromString("67184267-a970-476b-bc39-7d98b4619639"),
                name = "Second",
                money = 15.0,
                iconId = R.drawable.ic_8,
                color = Color.Red.toArgb(),
            ),
            ExpenseModel(
                id = UUID.fromString("9d53c392-2beb-45b9-b339-76ef6abd0c03"),
                name = "Thirty",
                money = 15.0,
                iconId = R.drawable.ic_10,
                color = Color.Blue.toArgb(),
            ),
            ExpenseModel(
                id = UUID.fromString("ae8c9817-191d-4a57-b135-51bc5ac04ca7"),
                name = "Four",
                money = 15.0,
                iconId = R.drawable.ic_heart,
                color = Color.Black.toArgb(),
            ),
        )

        val expensesWithTransactions = listOf(
            ExpenseWithTransactionsModel(
                expenseModel = ExpenseModel(
                    id = UUID.fromString("eb16beaa-2f2c-4263-89ae-f7635c793dfe"),
                    name = "Food",
                    money = 52512.0,
                    color = Color.Green.toArgb(),
                    iconId = R.drawable.ic_8
                ),
                transactions = listOf(
                    TransactionModel(
                        id = UUID.fromString("b7bd1817-7409-43ba-8e3b-ffd6a13f6a50"),
                        name = "Food",
                        cost = 421.0,
                        cashId = UUID.fromString("9452aa8d-e813-4f24-83ea-1786f1af2bc3"),
                        transactionType = TransactionType.EXPENSE,
                        date = "2022-06-03"
                    )
                )
            ),
            ExpenseWithTransactionsModel(
                expenseModel = ExpenseModel(
                    id = UUID.fromString("4be6d56b-ba1d-4811-8dff-c4688f92e1d7"),
                    name = "Transport",
                    money = 124512.0,
                    color = Color.Green.toArgb(),
                    iconId = R.drawable.ic_10
                ),
                transactions = listOf(
                    TransactionModel(
                        id = UUID.fromString("4fbdd0ed-ad27-424a-979e-b3461a9bbffc"),
                        name = "on school",
                        cost = 421.0,
                        cashId = UUID.fromString("9452aa8d-e813-4f24-83ea-1786f1af2bc3"),
                        transactionType = TransactionType.EXPENSE,
                        date = "2022-06-03"
                    ),
                    TransactionModel(
                        id = UUID.fromString("3a11f168-edd8-4fcb-8a3c-c55f7eb50cec"),
                        name = "from school",
                        cost = 351.0,
                        cashId = UUID.fromString("9452aa8d-e813-4f24-83ea-1786f1af2bc3"),
                        transactionType = TransactionType.EXPENSE,
                        date = "2022-06-03"
                    )
                )
            )
        )
        val cashList = listOf(
            CashModel(
                id = UUID.fromString("9452aa8d-e813-4f24-83ea-1786f1af2bc3"),
                name = "Наличные",
                icon = R.drawable.ic_cash_2,
                money = 1424.0
            ),
            CashModel(
                id = UUID.fromString("e2c66607-3c66-4928-86e8-1d3406587ea0"),
                name = "Карта",
                icon = R.drawable.ic_creditcard,
                money = 42421.412
            )
        )

        val incomeList = listOf(
            IncomeModel(
                id = UUID.fromString("e9916552-5cf6-4b9b-a7d8-2732ba7394c6"),
                name = "Стипендия",
                color = Color.Green.toArgb(),
                iconId = R.drawable.ic_cash_1,
                money = 3996.84,
            ),
            IncomeModel(
                id = UUID.fromString("436d333c-cc4f-45dc-a4e8-841115490bc0"),
                name = "Зарплата",
                color = Color.Green.toArgb(),
                iconId = R.drawable.ic_creditcard,
                money = 532526.84,
            )
        )

        val incomeWithTransactions = listOf(
            IncomeWithTransactionsModel(
                income = IncomeModel(
                    id = UUID.fromString("e9916552-5cf6-4b9b-a7d8-2732ba7394c6"),
                    name = "Зарплата",
                    color = Color.Green.toArgb(),
                    iconId = R.drawable.ic_cash_1,
                    money = 532526.84,
                ),
                transactions = listOf(
                    TransactionModel(
                        id = UUID.fromString("ff121937-4b5a-48e4-87a5-6cd9b9b3c8b6"),
                        name = "zp",
                        cost = 24102.0,
                        cashId = UUID.fromString("9452aa8d-e813-4f24-83ea-1786f1af2bc3"),
                        transactionType = TransactionType.INCOME,
                        date = "2022-06-03"
                    ),
                    TransactionModel(
                        id = UUID.fromString("3a4b075a-e81c-4ad5-b612-0db9c4c07ce6"),
                        name = "zp",
                        cost = 23512.0,
                        cashId = UUID.fromString("9452aa8d-e813-4f24-83ea-1786f1af2bc3"),
                        transactionType = TransactionType.INCOME,
                        date = "2022-06-03"
                    )
                )
            )
        )
    }
}