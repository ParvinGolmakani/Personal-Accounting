package com.pg.personalaccounting.view.account

import android.content.Intent
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseFragment
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : BaseFragment(R.layout.fragment_account) {

    companion object {
        private var INSTANCE: BaseFragment? = null
        fun getInstance(): BaseFragment {
            if (INSTANCE == null) {
                INSTANCE = AccountFragment()
            }
            return INSTANCE as BaseFragment
        }
    }

    override fun afterLoadView() {
        addAccountBtn.setOnClickListener {
            val intent = Intent(context, AddAccountActivity::class.java)
            startActivity(intent)

        }
    }

}