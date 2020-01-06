package com.pg.personalaccounting.view.account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pg.personalaccounting.R
import com.pg.personalaccounting.core.models.Account
import kotlinx.android.synthetic.main.item_account.view.*

class AccountAdapter : ListAdapter<Account, AccountAdapter.AccountViewHolder>(object :
    DiffUtil.ItemCallback<Account>() {
    override fun areItemsTheSame(oldItem: Account, newItem: Account): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Account, newItem: Account): Boolean {
        return oldItem == newItem
    }
}) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AccountViewHolder(inflater.inflate(R.layout.item_account, parent, false))
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(account: Account) {
            itemView.accountNumberTV.text = account.accNumber
            itemView.bankNameTV.text = account.bankName
            itemView.balanceTV.text = account.balance.toString()
        }
    }
}