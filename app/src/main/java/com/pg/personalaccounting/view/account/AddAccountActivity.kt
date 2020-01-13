package com.pg.personalaccounting.view.account


import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseActivity
import com.pg.personalaccounting.core.bases.BaseApplication
import com.pg.personalaccounting.core.models.Account
import kotlinx.android.synthetic.main.activity_add_account.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddAccountActivity : BaseActivity(R.layout.activity_add_account) {
    override fun afterLoadView() {
        buttonSaveAcc.setOnClickListener {
            saveAccount()
        }
    }

    private fun saveAccount() {
        GlobalScope.launch {
            val account = Account(
                0,
                accType.text.toString(),
                accNum.text.toString(),
                "$yearET-$monthET-$dayET",
                balance.text.toString().toFloat()
                ,
                bankName.text.toString()
            )
            BaseApplication.database.accountDao().insertAccount(account)
            withContext(Dispatchers.Main) {
                showToast("Account Saved")
                onBackPressed()
            }
        }
    }
}
