package com.pg.personalaccounting.view.home

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.CalendarView
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseFragment
import com.pg.personalaccounting.view.deposit_withdraw.DepositWithdrawActivity
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : BaseFragment(R.layout.fragment_home), CalendarView.OnDateChangeListener {

    companion object {
        private var INSTANCE: BaseFragment? = null
        fun getInstance(): BaseFragment {
            if (INSTANCE == null) {
                INSTANCE = HomeFragment()
            }
            return INSTANCE as BaseFragment
        }
    }

    override fun afterLoadView() {
        selectedDateTV.text = getTodayDate()
        calendarView.setOnDateChangeListener(this)
        depositBtn.setOnClickListener {
            activity!!.startActivity(Intent(activity,DepositWithdrawActivity::class.java))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onSelectedDayChange(view: CalendarView, year: Int, month: Int, day: Int) {
        selectedDateTV.text = "$day/${month + 1}/$year"
    }

    @SuppressLint("SimpleDateFormat")
    fun getTodayDate(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy")
        return sdf.format(Date())
    }
}