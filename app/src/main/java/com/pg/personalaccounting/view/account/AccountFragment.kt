package com.pg.personalaccounting.view.account

import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseFragment

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

    }

}