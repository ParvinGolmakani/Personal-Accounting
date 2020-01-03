package com.pg.personalaccounting.view.report

import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseFragment

class ReportFragment : BaseFragment(R.layout.fragment_report) {

    companion object {
        private var INSTANCE: BaseFragment? = null
        fun getInstance(): BaseFragment {
            if (INSTANCE == null) {
                INSTANCE = ReportFragment()
            }
            return INSTANCE as BaseFragment
        }
    }

    override fun afterLoadView() {

    }

}