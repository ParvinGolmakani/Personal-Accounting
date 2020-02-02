package com.pg.personalaccounting.view.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import androidx.recyclerview.widget.LinearLayoutManager
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseApplication
import com.pg.personalaccounting.core.bases.BaseFragment
import com.pg.personalaccounting.core.interfaces.UpdateDataInterface
import com.pg.personalaccounting.core.models.Transaction
import com.pg.personalaccounting.core.utils.AppConstants
import com.pg.personalaccounting.view.deposit_withdraw.DepositWithdrawActivity
import com.pg.personalaccounting.view.report.TransactionAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : BaseFragment(R.layout.fragment_home), CalendarView.OnDateChangeListener,
    UpdateDataInterface {

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

    private val transactionAdapter = TransactionAdapter()
    private var transactions = ArrayList<Transaction>()

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

        setMonthColor()

        initRecyclerView()
        getData()
    }

    private fun openDepositWithdrawPage(isDeposit: Boolean) {
        // set data for other page
        val bundle = Bundle()
        bundle.putInt("year", year)
        bundle.putInt("month", month)
        bundle.putInt("day", day)
        bundle.putBoolean("isDeposit", isDeposit)
        DepositWithdrawActivity.updateDataInterface = this
        activity!!.startActivity(
            Intent(activity, DepositWithdrawActivity::class.java).putExtras(
                bundle
            )
        )
    }

    private fun initRecyclerView() {
        transactionsRV.layoutManager = LinearLayoutManager(context)
        transactionsRV.adapter = transactionAdapter
    }

    @SuppressLint("SetTextI18n")
    override fun onSelectedDayChange(view: CalendarView, year: Int, month: Int, day: Int) {
        this.day = day
        this.month = month + 1
        this.year = year
        selectedDateTV.text = "${month + 1}/${day}/$year"
    }

    @SuppressLint("SimpleDateFormat")
    fun getTodayDate(): String {
        val c = Calendar.getInstance().time

        val sdf = SimpleDateFormat(AppConstants.dateFormat)
        val formattedDate = sdf.format(c)

        // split date to month day year
        val str = formattedDate.split("/")

        day = str[1].toInt()
        year = str[2].toInt()
        month = str[0].toInt()

        return sdf.format(Date())
    }

    private fun getData() {
        GlobalScope.launch {
            transactions =
                BaseApplication.database.transactionDao().getTopThreeTransaction() as ArrayList<Transaction>
            transactionAdapter.submitList(transactions)
        }
    }

    private fun setMonthColor() {

    }

    override fun updateDate() {
        getData()
    }
}