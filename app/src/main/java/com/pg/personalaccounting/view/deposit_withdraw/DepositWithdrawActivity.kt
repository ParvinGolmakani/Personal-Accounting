package com.pg.personalaccounting.view.deposit_withdraw

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.gson.Gson
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseActivity
import com.pg.personalaccounting.core.bases.BaseApplication
import com.pg.personalaccounting.core.interfaces.AccountInterface
import com.pg.personalaccounting.core.interfaces.UpdateDataInterface
import com.pg.personalaccounting.core.models.Account
import com.pg.personalaccounting.core.models.Transaction
import com.pg.personalaccounting.core.utils.dateToLong
import com.pg.personalaccounting.core.utils.getStringDate
import com.pg.personalaccounting.view.account.AccountDialog
import com.tomergoldst.timekeeper.core.TimeKeeper
import com.tomergoldst.timekeeper.model.Alarm
import kotlinx.android.synthetic.main.activity_deposit_withdraw.*
import kotlinx.android.synthetic.main.layout_date.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DepositWithdrawActivity() :
    BaseActivity(R.layout.activity_deposit_withdraw), AccountInterface {

    companion object {
        lateinit var updateDataInterface: UpdateDataInterface
    }

    private var isAccountSelected = false
    private lateinit var bundle: Bundle
    private lateinit var account: Account
    private val accountDialog = AccountDialog(this)
    private var isDeposit: Boolean = false
    override fun afterLoadView() {
        initViews()
    }

    private fun initViews() {

        // get date from main page calender view
        bundle = intent.extras!!
        yearET.setText(bundle.getInt("year").toString())
        monthET.setText(bundle.getInt("month").toString())
        dayET.setText(bundle.getInt("day").toString())

        // set page title
        isDeposit = bundle.getBoolean("isDeposit")
        setTitlePage()

        // show accounts dialog
        selectAccount.setOnClickListener {
            accountDialog.show(supportFragmentManager, accountDialog.tag)
        }

        // confirm button
        confirm.setOnClickListener {

            saveTransaction()
        }
        back.setOnClickListener { onBackPressed() }
    }

    private fun setTitlePage() {
        if (bundle.getBoolean("isDeposit")) {
            pageTitle.text = getString(R.string.deposit)
        } else {
            pageTitle.text = getString(R.string.withdraw)
        }
    }

    // on account clicked - account select dialog
    override fun getAccount(account: Account) {

        // set bank name in ui
        selectAccount.text = account.bankName
        this.account = account
        accountDialog.dismiss()
        isAccountSelected = true
    }

    @SuppressLint("NewApi")
    private fun saveTransaction() {

        // check data is entered
        if (amountET.text.toString().isNotEmpty()) {

            // check user selected account
            if (isAccountSelected) {
                GlobalScope.launch {

                    // convert entered date to milliseconds
                    val date = getStringDate(
                        yearET.text.toString(),
                        monthET.text.toString(),
                        dayET.text.toString()
                    )

                    // make object from Transaction by ui datas
                    val transaction = Transaction(
                        0,
                        isDeposit,
                        amountET.cleanDoubleValue,
                        dateToLong(date),
                        descET.text.toString(),
                        account.id,
                        !isCheck.isChecked
                    )

                    // is not Check
                    if (transaction.isCash) {

                        // save transaction
                        BaseApplication.database.transactionDao().insertTransaction(transaction)
                        // set balance
                        saveBalance(transaction)
                    } else {

                        // transaction using Check
                        // save transaction in alarm dialog page
                        setAlarm(transaction)
                    }

                    withContext(Dispatchers.Main) {
                        showToast("Saved!")
                        if (isCheck.isChecked) {
                            // alarm has been set
                            showToast("Alarm has been set")
                        }
                        updateDataInterface.updateDate()
                        onBackPressed()
                    }
                }

            } else {
                showToast("Insert Account")
            }

        } else {
            showToast("Please fill all fields")
        }

    }

    private fun saveBalance(transaction: Transaction) {

        if (!isCheck.isChecked) {
            // this is transaction is not check
            var sign = 1

            // get account from database by using transaction account id
            val account =
                BaseApplication.database.accountDao().getAccountById(transaction.accountId)
            if (!transaction.isDeposit) {
                sign = -1
            }

            // update account balance
            val amount = transaction.amount * sign
            account.balance = (account.balance + amount)
            BaseApplication.database.accountDao().updateAccount(account)
        }
    }

    private fun setAlarm(transaction: Transaction) {

        val alarm = Alarm("account", transaction.tDate)

        // send transaction in alarm : convert transaction to string and put it in ALARM and receive in alarm receiver
        alarm.payload = Gson().toJson(transaction)
        TimeKeeper.setAlarm(alarm)
    }
}
