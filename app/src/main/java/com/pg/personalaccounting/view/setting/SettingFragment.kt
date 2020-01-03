package com.pg.personalaccounting.view.setting

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseFragment
import kotlinx.android.synthetic.main.fragment_setting.view.*


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
        mView.share.setOnClickListener {
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
        mView.contactUs.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:09357773373")
            startActivity(intent)
        }
        mView.aboutUs.setOnClickListener {

                val aboutUs  = AboutUs(context!!)
                aboutUs.show()


        }
    }

}