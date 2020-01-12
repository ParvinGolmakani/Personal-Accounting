package com.pg.personalaccounting.core.bases

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseAdapter<T, VH : BaseViewHolder<T>>(
    diffUtil: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(diffUtil) {

    abstract val viewID: Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        return getViewHolder(inflater.inflate(viewID, parent, false), viewType)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(getItem(position))
        setClickListeners(holder, position)

    }

    abstract fun getViewHolder(parent: View, viewType: Int): VH

    abstract fun setClickListeners(holder: VH, position: Int)

}