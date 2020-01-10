package com.pg.personalaccounting.core.interfaces

import com.pg.personalaccounting.core.models.Account

interface AccountInterface {
    fun getAccount(account: Account)
}