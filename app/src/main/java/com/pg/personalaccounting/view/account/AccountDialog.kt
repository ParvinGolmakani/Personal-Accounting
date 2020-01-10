package com.pg.personalaccounting.view.account

import androidx.recyclerview.widget.LinearLayoutManager
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseBottomSheet
import com.pg.personalaccounting.core.interfaces.AccountInterface
import com.pg.personalaccounting.core.models.Account
import kotlinx.android.synthetic.main.dialog_account.view.*

class AccountDialog(private val accountInterface: AccountInterface) :
    BaseBottomSheet(R.layout.dialog_account) {


    lateinit var adapter: AccountAdapter

    override fun afterLoadView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = AccountAdapter(accountInterface)
        mView.accountsRV.layoutManager = LinearLayoutManager(context)
        mView.accountsRV.adapter = adapter
        adapter.submitList(addData())
    }

    private fun addData(): List<Account> {
        return ArrayList<Account>().apply {
            add(Account(0, "Type", "6037-7011-2121-1398", "12", 12000f, "MELLAT"))
            add(Account(0, "Type", "6037-7011-2121-1398", "12", 12000f, "KIR"))
            add(Account(0, "Type", "6037-7011-2121-1398", "12", 12000f, "KOON"))
            add(Account(0, "Type", "6037-7011-2121-1398", "12", 12000f, "MELLI"))
            add(Account(0, "Type", "6037-7011-2121-1398", "12", 12000f, "MELLI"))

            add(Account(0, "Type", "6037-7011-2121-1398", "12", 12000f, "MELLI"))
            add(Account(0, "Type", "6037-7011-2121-1398", "12", 12000f, "MELLI"))
            add(Account(0, "Type", "6037-7011-2121-1398", "12", 12000f, "MELLI"))
            add(Account(0, "Type", "6037-7011-2121-1398", "12", 12000f, "MELLI"))
            add(Account(0, "Type", "6037-7011-2121-1398", "12", 12000f, "MELLI"))

            add(Account(0, "Type", "6037-7011-2121-1398", "12", 12000f, "MELLI"))
            add(Account(0, "Type", "6037-7011-2121-1398", "12", 12000f, "MELLI"))
            add(Account(0, "Type", "6037-7011-2121-1398", "12", 12000f, "MELLI"))
            add(Account(0, "Type", "6037-7011-2121-1398", "12", 12000f, "MELLI"))
            add(Account(0, "Type", "6037-7011-2121-1398", "12", 12000f, "MELLI"))
        }
    }
}