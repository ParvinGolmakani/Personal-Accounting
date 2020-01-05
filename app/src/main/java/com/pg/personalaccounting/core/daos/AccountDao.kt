package com.pg.personalaccounting.core.daos

import androidx.room.*
import com.pg.personalaccounting.core.models.Account
import com.pg.personalaccounting.core.models.Transaction

@Dao
interface AccountDao {
    @Insert
    fun insertAccount(account: Account)

    @Update
    fun updateAccount(account: Account)

    @Delete
    fun deleteAccount(account: Account)

    @Query("select * from accounts")
    fun getAccount(): List<Account>
}