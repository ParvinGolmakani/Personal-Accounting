package com.pg.personalaccounting.view.account

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.bases.BaseAdapter
import com.pg.personalaccounting.core.bases.BaseViewHolder
import com.pg.personalaccounting.core.interfaces.AccountInterface
import com.pg.personalaccounting.core.models.Account
import kotlinx.android.synthetic.main.item_account.view.*

class AccountAdapter(private val accountInterface: AccountInterface) :
    BaseAdapter<Account, AccountAdapter.AccountViewHolder>(object :
        DiffUtil.ItemCallback<Account>() {
        override fun areItemsTheSame(oldItem: Account, newItem: Account): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: Account, newItem: Account): Boolean {
            return oldItem == newItem
        }
    }) {

    override val viewID: Int
        get() = R.layout.item_account

    override fun getViewHolder(parent: View, viewType: Int): AccountViewHolder {
        return AccountViewHolder(parent)
    }

    override fun setClickListeners(holder: AccountViewHolder, position: Int) {
        holder.itemView.mainLayout.setOnClickListener {
            accountInterface.getAccount(getItem(position))
        }
    }

    class AccountViewHolder(itemView: View) : BaseViewHolder<Account>(itemView) {
        override fun onBind(item: Account) {
            itemView.accountNumberTV.text = item.accNumber
            itemView.bankNameTV.text = item.bankName
            itemView.balanceTV.text = item.balance.toString()
        }
    }
}
