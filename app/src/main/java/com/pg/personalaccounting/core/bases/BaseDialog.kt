package com.pg.personalaccounting.core.bases

import android.app.Dialog
import android.content.Context
import android.os.Bundle

abstract class BaseDialog(context: Context?, private val viewID: Int) : Dialog(context!!) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewID)
        afterLoadView()
    }

    abstract fun afterLoadView()
}