package com.pg.personalaccounting.core.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = true) val id: Int, val accType: String,
    val accNumber: String,
    val accDate: String,
    var balance: Double,
    val bankName: String
)