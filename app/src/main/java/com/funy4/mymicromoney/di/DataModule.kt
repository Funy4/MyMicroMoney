package com.funy4.mymicromoney.di

import android.content.Context
import androidx.room.Room
import com.funy4.data.db.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        Database::class.java,
        "micro_database"
    ).build()

    @Provides
    @Singleton
    fun provideCashDao(database: Database) = database.cashDao()

    @Provides
    @Singleton
    fun provideIncomeDao(database: Database) = database.incomeDao()

    @Provides
    @Singleton
    fun provideTransactionDao(database: Database) = database.transactionDao()

    @Provides
    @Singleton
    fun provideExpensesDao(database: Database) = database.expensesDao()

}