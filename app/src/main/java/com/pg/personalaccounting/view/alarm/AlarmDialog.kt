package com.pg.personalaccounting.view.alarm

import android.annotation.SuppressLint
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseActivity
import com.pg.personalaccounting.core.bases.BaseApplication
import com.pg.personalaccounting.core.models.Account
import com.pg.personalaccounting.core.models.Transaction
import com.pg.personalaccounting.view.deposit_withdraw.DepositWithdrawActivity
import kotlinx.android.synthetic.main.dialog_alarm_dialog.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlarmDialog :
    BaseActivity(R.layout.dialog_alarm_dialog) {

    companion object {
        lateinit var transaction: Transaction
    }

    lateinit var account: Account


    override fun afterLoadView() {
        getData()
        confirm.setOnClickListener {
            saveTransaction()
        }
        if (transaction.isDeposit) {
            pageTitle.text = getString(R.string.withdraw)
        } else {
            pageTitle.text = getString(R.string.deposit)
        }
    }


    private fun getData() {
        GlobalScope.launch {
            account = BaseApplication.database.accountDao().getAccountById(transaction.accountId)
            withContext(Dispatchers.Main) {
                initView()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        amountET.setText(transaction.amount.toString())
        descET.setText(transaction.desc)
        selectAccount.setText("${account.bankName} \n${account.accNumber}")
    }

    @SuppressLint("NewApi")
    private fun saveTransaction() {
        if (amountET.text.toString().isNotEmpty()) {
            GlobalScope.launch {
                BaseApplication.database.transactionDao().insertTransaction(transaction)

                // set balance
                saveBalance(transaction)

                withContext(Dispatchers.Main) {
                    showToast("DONE!")
                    DepositWithdrawActivity.updateDataInterface.updateDate()
                    onBackPressed()
                }
            }
        }

    }

    private fun saveBalance(transaction: Transaction) {

        var sign = 1
        if (!transaction.isDeposit) {
            sign = -1
        }
        val amount = transaction.amount * sign
        account.balance = (account.balance + amount)
        BaseApplication.database.accountDao().updateAccount(account)

    }

}