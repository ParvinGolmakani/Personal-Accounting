package com.pg.personalaccounting.view.main

import android.view.MenuItem
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseActivity
import com.pg.personalaccounting.core.bases.BaseFragment
import com.pg.personalaccounting.view.account.AccountFragment
import com.pg.personalaccounting.view.home.HomeFragment
import com.pg.personalaccounting.view.report.ReportFragment
import com.pg.personalaccounting.view.setting.SettingFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(R.layout.activity_main),
    BottomNavigationView.OnNavigationItemSelectedListener {

    var activeFragment = HomeFragment.getInstance()

    override fun afterLoadView() {
        initBottomNavigation()
        bottomNavigation.selectedItemId = R.id.navHome
    }

    private fun initBottomNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener(this)
    }


    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        when (menuItem.itemId) {
            R.id.navHome -> {
                showFragment(HomeFragment.getInstance(), "1")

            }
            R.id.navAccount -> {
                showFragment(AccountFragment.getInstance(), "2")
            }
            R.id.navReport -> {
                showFragment(ReportFragment.getInstance(), "3")
            }
            R.id.navSetting -> {
                showFragment(SettingFragment.getInstance(), "4")
            }
        }
        return true
    }

    private fun showFragment(fragment: BaseFragment, tag: String) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()

        // hide last fragment
        if (fragment != activeFragment)
            ft.hide(activeFragment)

        // show current fragment
        if (fragment.isAdded) {
            ft.show(fragment)
        } else {
            ft.add(R.id.fragmentContainer, fragment, tag)
        }
        ft.commit()

        // set active fragment
        activeFragment = fragment
    }

    override fun onResume() {
        super.onResume()
    }
}
