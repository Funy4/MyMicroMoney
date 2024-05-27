package com.funy4.mymicromoney.ui

import com.funy4.mymicromoney.R

class IconUtil{
    companion object{
        val allCashIconsIds = listOf(
            R.drawable.ic_cash_1,
            R.drawable.ic_cash_2,
            R.drawable.ic_cash_3,
            R.drawable.ic_cash_4,
        )
        val allExpensesIncomeIconsIds = listOf(
            R.drawable.ic_1,
            R.drawable.ic_2,
            R.drawable.ic_3,
            R.drawable.ic_4,
            R.drawable.ic_5,
            R.drawable.ic_6,
            R.drawable.ic_7,
        )
    }
}

fun getCashIconDrawableId(id: Int) = when (id) {
    1 -> R.drawable.ic_cash_1
    2 -> R.drawable.ic_cash_2
    3 -> R.drawable.ic_cash_3
    4 -> R.drawable.ic_cash_4
    else -> R.drawable.ic_cash_1
}

fun getIconDrawableId(id: Int) = when(id){
    1 -> R.drawable.ic_1
    2 -> R.drawable.ic_2
    3 -> R.drawable.ic_3
    4 -> R.drawable.ic_4
    5 -> R.drawable.ic_5
    6 -> R.drawable.ic_6
    7 -> R.drawable.ic_7
    else -> R.drawable.ic_1
}