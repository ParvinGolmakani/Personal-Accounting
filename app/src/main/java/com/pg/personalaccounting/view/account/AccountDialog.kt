package com.pg.personalaccounting.view.account

import androidx.recyclerview.widget.LinearLayoutManager
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseBottomSheet
import com.pg.personalaccounting.core.models.Account
import kotlinx.android.synthetic.main.dialog_account.view.*

class AccountDialog : BaseBottomSheet(R.layout.dialog_account) {

    companion object {
        private var INSTANCE: BaseBottomSheet? = null
        fun getInstance(): BaseBottomSheet {
            if (INSTANCE == null) {
                INSTANCE = AccountDialog()
            }
            return INSTANCE as BaseBottomSheet
        }
    }

    lateinit var adapter: AccountAdapter

    override fun afterLoadView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = AccountAdapter()
        mView.accountsRV.layoutManager = LinearLayoutManager(context)
        mView.accountsRV.adapter = adapter
        adapter.submitList(addData())
    }

    fun addData(): List<Account> {
        return ArrayList<Account>().apply {
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
            add(Account(0, "Type", "6037-7011-2121-1398", "12", 12000f, "MELLI"))
            add(Account(0, "Type", "6037-7011-2121-1398", "12", 12000f, "MELLI"))
            add(Account(0, "Type", "6037-7011-2121-1398", "12", 12000f, "MELLI"))
        }
    }
}