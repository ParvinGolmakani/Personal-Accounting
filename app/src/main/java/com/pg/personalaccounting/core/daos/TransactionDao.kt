package com.pg.personalaccounting.core.daos

import androidx.room.*
import com.pg.personalaccounting.core.models.Transaction

@Dao
interface TransactionDao {
    @Insert
    fun insertTransaction(transaction: Transaction)

    @Update
    fun updateTransaction(transaction: Transaction)

    @Delete
    fun deleteTransaction(transaction: Transaction)

    @Query("select * from transactions")
    fun getTransaction(): List<Transaction>

    @Query("select * from transactions where tDate between :from and :to ")
    fun getTransactionByDates(from: Long, to: Long): List<Transaction>

    @Query("SELECT * FROM transactions ORDER BY id DESC LIMIT 3")
    fun getTopThreeTransaction(): List<Transaction>

    @Query("SELECT * FROM transactions where transactions.`desc` like :word")
    fun searchTransactions(word: String): List<Transaction>
}