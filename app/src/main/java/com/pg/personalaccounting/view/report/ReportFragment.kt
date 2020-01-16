package com.pg.personalaccounting.view.report

import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseApplication
import com.pg.personalaccounting.core.bases.BaseFragment
import com.pg.personalaccounting.core.models.Transaction
import kotlinx.android.synthetic.main.fragment_report.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReportFragment : BaseFragment(R.layout.fragment_report) {

    companion object {
        private var INSTANCE: BaseFragment? = null
        fun getInstance(): BaseFragment {
            if (INSTANCE == null) {
                INSTANCE = ReportFragment()
            }
            return INSTANCE as BaseFragment
        }
    }

    // init values
    private val transactionAdapter = TransactionAdapter()
    var list = ArrayList<Transaction>()

    override fun afterLoadView() {
        initRV()
    }

    private fun initRV() {
        transactionsRV.layoutManager = LinearLayoutManager(context)
        transactionsRV.adapter = transactionAdapter

        searchET.addTextChangedListener {
            if (it!!.isNotEmpty())
                search(it.toString())
            else
                getData()
        }
    }

    private fun getData() {
        GlobalScope.launch {
            list =
                BaseApplication.database.transactionDao().getTransaction() as ArrayList<Transaction>

            withContext(Dispatchers.Main) {
                transactionAdapter.submitList(list)
            }
        }
    }

    private fun search(word: String) {
        GlobalScope.launch {
            list =
                BaseApplication.database.transactionDao().searchTransactions(word) as ArrayList<Transaction>

            withContext(Dispatchers.Main) {
                transactionAdapter.submitList(list)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }
}