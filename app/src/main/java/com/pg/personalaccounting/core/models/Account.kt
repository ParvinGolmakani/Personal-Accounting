package com.pg.personalaccounting.core.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "accounts")
data class Account(@PrimaryKey(autoGenerate = true) val id:Int, val accType:String, val accNumber: Int, val accDate:String, val balance:Float, val bankName:String)