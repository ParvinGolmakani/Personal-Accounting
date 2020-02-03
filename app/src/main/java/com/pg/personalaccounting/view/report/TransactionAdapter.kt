package com.pg.personalaccounting.view.report

import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseAdapter
import com.pg.personalaccounting.core.bases.BaseApplication
import com.pg.personalaccounting.core.bases.BaseViewHolder
import com.pg.personalaccounting.core.models.Transaction
import com.pg.personalaccounting.core.utils.longToDate
import kotlinx.android.synthetic.main.item_transaction.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionAdapter :
    BaseAdapter<Transaction, TransactionAdapter.TransactionViewHolder>(object :
        DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }

    }) {
    override val viewID: Int
        get() = R.layout.item_transaction


    override fun getViewHolder(parent: View, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(parent)
    }

    override fun setClickListeners(holder: TransactionViewHolder, position: Int) {
    }

    class TransactionViewHolder(itemView: View) : BaseViewHolder<Transaction>(itemView) {
        var amountSign = "+"

        @SuppressLint("SetTextI18n")
        override fun onBind(item: Transaction) {

            // set plus or negative sign
            setSign(itemView, item)

            // set account information
            val account = getAccountData(itemView, item, item.accountId)


            // set transaction amount
            itemView.amountTV.text = "$amountSign${item.amount}$"
        }

        // set transaction icon and sign
        private fun setSign(itemView: View, transaction: Transaction) {
            if (transaction.isDeposit) {
                amountSign = "+"
                itemView.icon.setImageResource(R.drawable.ic_deposit)
                itemView.cardImage.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.green
                    )
                )
            } else {
                amountSign = "-"
                itemView.icon.setImageResource(R.drawable.ic_withdraw)
                itemView.cardImage.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.orange
                    )
                )
            }
        }


        @SuppressLint("SetTextI18n")
        private fun getAccountData(
            itemView: View,
            transaction: Transaction,
            id: Int
        ) {
            GlobalScope.launch {
                val account = BaseApplication.database.accountDao().getAccountById(id)
                withContext(Dispatchers.Main) {
                    itemView.bankDateTV.text =
                        "${account.bankName} - ${longToDate(transaction.tDate)}"
                    itemView.bankAccountNumber.text = account?.accNumber
                }
            }

        }

    }
}