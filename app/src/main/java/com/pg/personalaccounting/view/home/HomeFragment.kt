package com.pg.personalaccounting.view.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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

    // init values
    var day: Int = 0
    var month: Int = 0
    var year: Int = 0

    override fun afterLoadView() {

        // calender view config
        selectedDateTV.text = getTodayDate()
        calendarView.setOnDateChangeListener(this)

        // on clicks
        depositBtn.setOnClickListener {
            openDepositWithdrawPage(true)
        }
        withdrawBtn.setOnClickListener {
            openDepositWithdrawPage(false)
        }
    }

    private fun openDepositWithdrawPage(isDeposit: Boolean) {
        // set data for other page
        val bundle = Bundle()
        bundle.putInt("year", year)
        bundle.putInt("month", month)
        bundle.putInt("day", day)
        bundle.putBoolean("isDeposit", isDeposit)

        activity!!.startActivity(
            Intent(activity, DepositWithdrawActivity::class.java).putExtras(
                bundle
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onSelectedDayChange(view: CalendarView, year: Int, month: Int, day: Int) {
        this.day = day
        this.month = month + 1
        this.year = year
        selectedDateTV.text = "$year/${month + 1}/$day"
    }

    @SuppressLint("SimpleDateFormat")
    fun getTodayDate(): String {
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        val date = Date()
        day = date.day
        year = date.year
        month = date.month + 1
        return sdf.format(Date())
    }
}