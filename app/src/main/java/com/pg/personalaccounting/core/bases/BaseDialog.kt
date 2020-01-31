package com.pg.personalaccounting.core.bases

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Toast

abstract class BaseDialog(context: Context?, private val viewID: Int) : Dialog(context!!) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(viewID)
        afterLoadView()
    }

    fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    abstract fun afterLoadView()
}