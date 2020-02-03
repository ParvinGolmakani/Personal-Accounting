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
        back.setOnClickListener { onBackPressed() }
    }

    private fun saveAccount() {

        // check all of fields are entered
        if (accType.text.toString().isNotEmpty() and accNum.text.toString().isNotEmpty() and balance.text.toString().isNotEmpty()) {
            try {
                GlobalScope.launch {

                    // make an object from Account
                    // id = 0 means generate id
                    val account = Account(
                        0,
                        accType.text.toString(),
                        accNum.text.toString(),
                        "${yearET.text}-${monthET.text}-${dayET.text}",
                        balance.cleanDoubleValue
                        ,
                        bankName.text.toString()
                    )

                    // save account
                    BaseApplication.database.accountDao().insertAccount(account)
                    withContext(Dispatchers.Main) {
                        // show message
                        showToast("Account Saved")
                        onBackPressed()
                    }
                }
            } catch (e: Exception) {
                showToast("Entered data in incorrect")
            }
        } else {
            showToast("Please fill all fields")
        }

    }
}
