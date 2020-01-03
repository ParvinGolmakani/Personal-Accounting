package com.pg.personalaccounting.view.main

import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseActivity
import com.pg.personalaccounting.view.account.AccountFragment
import com.pg.personalaccounting.view.home.HomeFragment
import com.pg.personalaccounting.view.report.ReportFragment
import com.pg.personalaccounting.view.setting.SettingFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(R.layout.activity_main) ,BottomNavigationView.OnNavigationItemSelectedListener{

    override fun afterLoadView() {
        initBottomNavigation()

    }
    private fun initBottomNavigation(){
        bottomNavigation.setOnNavigationItemSelectedListener(this)
        bottomNavigation.selectedItemId = R.id.navHome
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        var fragment: Fragment? = null
        when (menuItem.itemId) {
            R.id.navHome -> fragment = HomeFragment.getInstance()
            R.id.navAccount -> fragment = AccountFragment.getInstance()
            R.id.navReport -> fragment = ReportFragment.getInstance()
            R.id.navSetting -> fragment = SettingFragment.getInstance()
        }
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, fragment!!)
        transaction.commit()
        return true
    }
}
