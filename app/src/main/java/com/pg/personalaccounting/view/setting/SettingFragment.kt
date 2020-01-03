package com.pg.personalaccounting.view.setting

import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseFragment

class SettingFragment : BaseFragment(R.layout.fragment_setting) {

    companion object {
        private var INSTANCE: BaseFragment? = null
        fun getInstance(): BaseFragment {
            if (INSTANCE == null) {
                INSTANCE = SettingFragment()
            }
            return INSTANCE as BaseFragment
        }
    }

    override fun afterLoadView() {

    }

}