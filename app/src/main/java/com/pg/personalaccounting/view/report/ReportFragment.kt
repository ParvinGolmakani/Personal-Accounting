package com.pg.personalaccounting.view.report

import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseApplication
import com.pg.personalaccounting.core.bases.BaseFragment
import com.pg.personalaccounting.core.interfaces.FilterInterface
import com.pg.personalaccounting.core.models.Transaction
import kotlinx.android.synthetic.main.fragment_report.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReportFragment : BaseFragment(R.layout.fragment_report), FilterInterface {

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

    // filter and sort dialog
    lateinit var dialog: ReportFilterDialog

    override fun afterLoadView() {
        initRV()

        // init dialog
        dialog = ReportFilterDialog(context!!, this)
        filterIV.setOnClickListener {
            dialog.show()
        }
    }

    private fun initRV() {
        transactionsRV.layoutManager = LinearLayoutManager(context)
        transactionsRV.adapter = transactionAdapter

        // search
        searchET.addTextChangedListener {
            if (it!!.isNotEmpty())
                search(it.toString())
            else
            // if search field is empty
                getData()
        }
    }

    private fun getData() {
        // get transactions from database
        GlobalScope.launch {
            list =
                BaseApplication.database.transactionDao().getTransaction() as ArrayList<Transaction>

            withContext(Dispatchers.Main) {
                // data has been received , now has been shown on UI
                transactionAdapter.submitList(list)
            }
        }
    }


    private fun search(word: String) {
        // search from database
        GlobalScope.launch {
            list =
                BaseApplication.database.transactionDao().searchTransactions("%$word%") as ArrayList<Transaction>

            withContext(Dispatchers.Main) {
                // data has been received , now has been shown on UI
                transactionAdapter.submitList(list)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun getDates(from: Long, to: Long) {

        // show transaction between 2 dates , from , to
        GlobalScope.launch {
            list =
                BaseApplication.database.transactionDao().getTransactionByDates(
                    from,
                    to
                ) as ArrayList<Transaction>

            withContext(Dispatchers.Main) {

                // update ui
                transactionAdapter.submitList(list)
                showToast("Done!")
            }
        }
    }

    override fun clearFilter() {
        getData()
        dialog = ReportFilterDialog(context!!, this)
    }
}