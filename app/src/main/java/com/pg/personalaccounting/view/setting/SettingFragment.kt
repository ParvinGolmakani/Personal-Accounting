package com.pg.personalaccounting.view.setting

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.AppDataBase
import com.pg.personalaccounting.core.bases.BaseApplication
import com.pg.personalaccounting.core.bases.BaseFragment
import com.pg.personalaccounting.core.models.Account
import com.pg.personalaccounting.core.utils.AppPreferences
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_setting.view.*
import kotlinx.coroutines.*


class SettingFragment : BaseFragment(R.layout.fragment_setting) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: BaseFragment? = null

        fun getInstance(): BaseFragment {
            if (INSTANCE == null) {
                INSTANCE = SettingFragment()
            }
            return INSTANCE as BaseFragment
        }
    }

    override fun afterLoadView() {

        nameTV.setOnClickListener {
            saveAccount()
        }
        mView.share.setOnClickListener {
            shareApp()
        }
        mView.contactUs.setOnClickListener {
            contactUs()
        }
        mView.aboutUs.setOnClickListener {
            val aboutUs = AboutUsDialog(context!!)
            aboutUs.show()
        }
        mView.themeButton.setOnClickListener {
            if(AppPreferences.getTheme()==R.style.LightTheme){
                AppPreferences.saveTheme(R.style.DarkTheme)

            }
           else{
                AppPreferences.saveTheme(R.style.LightTheme)
            }
            activity?.recreate()
        }
    }

    fun shareApp() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                    Intent.EXTRA_TEXT,
                    "I use the Personal Accountant app and I suggest you use it too"
            )
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    fun contactUs() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:09357773373")
        startActivity(intent)
    }

    private fun saveAccount() {
        GlobalScope.launch {
            val account = Account(0, "type0", "112233", "mydate", 1000f, "mellat")
            BaseApplication.database.accountDao().insertAccount(account)
            withContext(Dispatchers.Main) {
                showToast("Account Saved")
            }
        }
    }

}