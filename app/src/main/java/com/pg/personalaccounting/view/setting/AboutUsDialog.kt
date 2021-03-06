package com.pg.personalaccounting.view.setting

import android.content.Context
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseDialog
import kotlinx.android.synthetic.main.dialog_about_us.*

class AboutUsDialog(context: Context) : BaseDialog(context, R.layout.dialog_about_us) {
    override fun afterLoadView() {
        ok.setOnClickListener {
            dismiss()
        }
    }
}