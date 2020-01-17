package com.pg.personalaccounting.view.report

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseDialog
import com.pg.personalaccounting.core.interfaces.FilterInterface
import com.pg.personalaccounting.core.utils.dateToLong
import com.pg.personalaccounting.core.utils.getStringDate
import kotlinx.android.synthetic.main.dialog_report_sort.*
import kotlinx.android.synthetic.main.layout_date.view.*

class ReportFilterDialog(context: Context, private val filterInterface: FilterInterface) :
    BaseDialog(context, R.layout.dialog_report_sort) {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun afterLoadView() {
        initViews()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initViews() {
        filterBTN.setOnClickListener {
            val fromDate = getStringDate(
                fromLayout.yearET.text.toString(),
                fromLayout.monthET.text.toString(),
                fromLayout.dayET.text.toString()
            )

            val toDate = getStringDate(
                toLayout.yearET.text.toString(),
                toLayout.monthET.text.toString(),
                toLayout.dayET.text.toString()
            )
            filterInterface.getDates(dateToLong(fromDate), dateToLong(toDate))
            dismiss()
        }
    }

}