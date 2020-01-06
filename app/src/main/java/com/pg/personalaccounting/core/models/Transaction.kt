package com.pg.personalaccounting.core.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(@PrimaryKey(autoGenerate = true) val id:Int, val amount:Float, val tDate: String, val desc:String, val accountId:Int, val isCash:Boolean)