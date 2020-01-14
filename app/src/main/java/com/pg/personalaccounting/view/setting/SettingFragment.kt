package com.pg.personalaccounting.view.setting

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseFragment
import com.pg.personalaccounting.core.interfaces.NameInterface
import com.pg.personalaccounting.core.utils.AppPreferences
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_setting.view.*

class SettingFragment : BaseFragment(R.layout.fragment_setting), NameInterface {


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
        editName()

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
            if (AppPreferences.getTheme() == R.style.LightTheme) {
                AppPreferences.saveTheme(R.style.DarkTheme)

            } else {
                AppPreferences.saveTheme(R.style.LightTheme)
            }
            activity?.recreate()

        }
        mView.nameTV.setOnClickListener {
            val dialog = NameDialog(this.context!!, this)
            dialog.show()

        }
    }

    private fun editName() {
        nameTV.text = AppPreferences.getName()
    }

    private fun shareApp() {
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

    private fun contactUs() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:09357773373")
        startActivity(intent)
    }

    override fun changeName(name: String) {
        AppPreferences.saveName(name)
        nameTV.text = AppPreferences.getName()
    }

    fun restartApplication() {
        val packageManager: PackageManager = context!!.packageManager
        val intent = packageManager.getLaunchIntentForPackage(context!!.packageName)
        val componentName = intent!!.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        context!!.startActivity(mainIntent)
        Runtime.getRuntime().exit(0)
    }
}