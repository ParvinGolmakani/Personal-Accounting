package com.pg.personalaccounting.view.account

import android.content.Intent
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseApplication
import com.pg.personalaccounting.core.bases.BaseFragment
import com.pg.personalaccounting.core.interfaces.AccountInterface
import com.pg.personalaccounting.core.models.Account
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.layout_empty_account.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountFragment : BaseFragment(R.layout.fragment_account), AccountInterface {

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
            openAddAccount()
        }
        addAccountTV.setOnClickListener {
            openAddAccount()
        }
        initRV()
    }

    private fun openAddAccount() {
        val intent = Intent(context, AddAccountActivity::class.java)
        startActivity(intent)
    }

    private fun initRV() {
        AccountList.layoutManager = LinearLayoutManager(context)
        AccountList.adapter = accountAdapter
        searchAc.addTextChangedListener {
            if (it!!.isNotEmpty())
                search(it.toString())
            else
                getData()
        }
    }

    private fun getData() {
        GlobalScope.launch {
            list =
                BaseApplication.database.accountDao().getAccount() as ArrayList<Account>

            withContext(Dispatchers.Main) {
                accountAdapter.submitList(list)
                if (list.size == 0) {
                    emptyLayout.visibility = View.VISIBLE
                    AccountList.visibility = View.GONE
                } else {
                    emptyLayout.visibility = View.GONE
                    AccountList.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun search(word: String) {
        GlobalScope.launch {
            list =
                BaseApplication.database.accountDao().searchAccount(word) as ArrayList<Account>

            withContext(Dispatchers.Main) {
                accountAdapter.submitList(list)
            }
        }
    }

    override fun getAccount(account: Account) {
        EditAccount.selectedAccount = account
        context?.startActivity(Intent(context, EditAccount::class.java))
    }

    override fun onResume() {
        super.onResume()
        getData()
    }
}