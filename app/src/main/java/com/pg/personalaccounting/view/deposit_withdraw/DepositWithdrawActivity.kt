package com.pg.personalaccounting.view.deposit_withdraw

import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseActivity
import com.pg.personalaccounting.view.account.AccountDialog
import kotlinx.android.synthetic.main.activity_deposit_withdraw.*

class DepositWithdrawActivity : BaseActivity(R.layout.activity_deposit_withdraw) {
    override fun afterLoadView() {
        selectAccount.setOnClickListener {
            AccountDialog.getInstance()
                .show(supportFragmentManager, AccountDialog.getInstance().tag)
        }
    }


}
