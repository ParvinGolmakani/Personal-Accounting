package com.pg.personalaccounting.view.home

import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseFragment

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    companion object {
        private var INSTANCE: BaseFragment? = null
        fun getInstance(): BaseFragment {
            if (INSTANCE == null) {
                INSTANCE = HomeFragment()
            }
            return INSTANCE as BaseFragment
        }
    }

    override fun afterLoadView() {

    }

}