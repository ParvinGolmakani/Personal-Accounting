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
}