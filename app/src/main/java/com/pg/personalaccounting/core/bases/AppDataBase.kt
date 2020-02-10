package com.pg.personalaccounting.core.bases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pg.personalaccounting.core.daos.AccountDao
import com.pg.personalaccounting.core.daos.TransactionDao
import com.pg.personalaccounting.core.models.Account
import com.pg.personalaccounting.core.models.Transaction

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [Account::class, Transaction::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao
    abstract fun accountDao(): AccountDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "my_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}