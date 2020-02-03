package com.pg.personalaccounting.view.setting

import android.content.Context
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseDialog
import com.pg.personalaccounting.core.interfaces.NameInterface
import kotlinx.android.synthetic.main.edit_name_dialog.*

class NameDialog(context: Context, private val nameInterface: NameInterface) :
    BaseDialog(context, R.layout.edit_name_dialog) {
    override fun afterLoadView() {
        // on save name clicked
        saveName.setOnClickListener {

            // send name to Setting Fragment
            nameInterface.changeName(yourname.text.toString())
            dismiss()
        }
    }
}