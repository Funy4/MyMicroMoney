package com.funy4.mymicromoney.di

import com.funy4.data.repo.ApiRepoImpl
import com.funy4.data.repo.CashRepoImpl
import com.funy4.data.repo.ExpensesRepoImpl
import com.funy4.data.repo.IncomeRepoImpl
import com.funy4.data.repo.TransactionRepoImpl
import com.funy4.domain.repo.ApiRepo
import com.funy4.domain.repo.CashRepo
import com.funy4.domain.repo.ExpensesRepo
import com.funy4.domain.repo.IncomeRepo
import com.funy4.domain.repo.TransactionRepo
import com.funy4.domain.usecase.cash.AddCashUseCase
import com.funy4.domain.usecase.cash.DeleteCashUseCase
import com.funy4.domain.usecase.cash.UpdateCashUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainBinds {
    @Binds
    @Singleton
    fun bindCashRepo(cashRepoImpl: CashRepoImpl): CashRepo

    @Binds
    @Singleton
    fun bindExpensesRepo(expensesRepoImpl: ExpensesRepoImpl): ExpensesRepo

    @Binds
    @Singleton
    fun bindIncomeRepo(incomeRepoImpl: IncomeRepoImpl): IncomeRepo

    @Binds
    @Singleton
    fun bindTransactionRepo(transactionRepoImpl: TransactionRepoImpl): TransactionRepo

    @Binds
    @Singleton
    fun bindApiRepo(apiRepoImpl: ApiRepoImpl): ApiRepo
}