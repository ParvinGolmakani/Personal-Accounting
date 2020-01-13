package com.pg.personalaccounting.view.account

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseApplication
import com.pg.personalaccounting.core.bases.BaseBottomSheet
import com.pg.personalaccounting.core.interfaces.AccountInterface
import com.pg.personalaccounting.core.models.Account
import kotlinx.android.synthetic.main.dialog_account.*
import kotlinx.android.synthetic.main.dialog_account.view.*
import kotlinx.android.synthetic.main.layout_empty_account.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AccountDialog(private val accountInterface: AccountInterface) :
    BaseBottomSheet(R.layout.dialog_account) {


    lateinit var adapter: AccountAdapter
    private var accounts = ArrayList<Account>()
    override fun afterLoadView() {
        initRecyclerView()
        getAccounts()
        addAccountTV.setOnClickListener {
            dismiss()
            openAccountActivity()
        }
        addAccountTV2.setOnClickListener {
            dismiss()
            openAccountActivity()
        }

    }

    private fun initRecyclerView() {
        adapter = AccountAdapter(accountInterface)
        mView.accountsRV.layoutManager = LinearLayoutManager(context)
        mView.accountsRV.adapter = adapter
    }

    private fun getAccounts() {
        GlobalScope.launch {
            accounts = BaseApplication.database.accountDao().getAccount() as ArrayList<Account>
            adapter.submitList(accounts)
            handleEmptyLayout()
        }
    }

    private fun handleEmptyLayout() {
        if (accounts.isEmpty()) {
            emptyLayout.visibility = View.VISIBLE
            dialogLayout.visibility = View.GONE
        } else {
            emptyLayout.visibility = View.GONE
            dialogLayout.visibility = View.VISIBLE
        }
    }

    private fun openAccountActivity() {
        val intent = Intent(context, AddAccountActivity::class.java)
        startActivity(intent)
    }
}