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

class EditAccount : BaseActivity(R.layout.activity_add_account) {

    companion object {
        lateinit var selectedAccount: Account
    }

    override fun afterLoadView() {
        initView()
        buttonSaveAcc.setOnClickListener {
            updateAccount()
        }
    }

    private fun initView() {
        bankName.setText(selectedAccount.bankName)
        accType.setText(selectedAccount.accType)
        accNum.setText(selectedAccount.accNumber)
        balance.setText(selectedAccount.balance.toString())

        // convert string to date
        val strs = selectedAccount.accDate.split("-").toTypedArray()
        yearET.setText(strs[0])
        monthET.setText(strs[1])
        dayET.setText(strs[2])
    }

    private fun updateAccount() {
        GlobalScope.launch {
            val selectedAccount = Account(
                selectedAccount.id,
                accType.text.toString(),
                accNum.text.toString(),
                "${yearET.text}-${monthET.text}-${dayET.text}",
                balance.cleanDoubleValue

                ,
                bankName.text.toString()
            )
            BaseApplication.database.accountDao().updateAccount(selectedAccount)
            withContext(Dispatchers.Main) {
                showToast("Account Saved")
                onBackPressed()
            }
        }
    }

}
