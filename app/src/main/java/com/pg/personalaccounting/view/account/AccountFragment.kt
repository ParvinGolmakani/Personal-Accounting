package com.pg.personalaccounting.view.account

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseApplication
import com.pg.personalaccounting.core.bases.BaseFragment
import com.pg.personalaccounting.core.interfaces.AccountInterface
import com.pg.personalaccounting.core.models.Account
import com.pg.personalaccounting.core.models.Transaction
import com.pg.personalaccounting.view.report.TransactionAdapter
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_report.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountFragment : BaseFragment(R.layout.fragment_account),AccountInterface {
    override fun getAccount(account: Account) {

    }

    companion object {
        private var INSTANCE: BaseFragment? = null
        fun getInstance(): BaseFragment {
            if (INSTANCE == null) {
                INSTANCE = AccountFragment()
            }
            return INSTANCE as BaseFragment
        }
    }
    private val accountAdapter = AccountAdapter(this)
    var list = ArrayList<Account>()


    override fun afterLoadView() {
        addAccountBtn.setOnClickListener {
            val intent = Intent(context, AddAccountActivity::class.java)
            startActivity(intent)

        }
        initRV()
    }



private fun initRV() {
    AccountList.layoutManager = LinearLayoutManager(context)
    AccountList.adapter = accountAdapter
}

private fun getData() {
    GlobalScope.launch {
        list =
                BaseApplication.database.accountDao().getAccount() as ArrayList<Account>

        withContext(Dispatchers.Main) {
            accountAdapter.submitList(list)
        }
    }
}

override fun onResume() {
    super.onResume()
    getData()
}
}