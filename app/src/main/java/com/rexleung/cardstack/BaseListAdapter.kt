package com.rexleung.cardstack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by rexchung on 22/10/2023.
 */
abstract class BaseListAdapter<T>(
    diffCallback: BaseDiffCallback<T>,
) : ListAdapter<T, BaseListAdapter.BaseViewHolder<T>>(diffCallback) {
    var onItemClickListener: ((ItemClick) -> Unit)? = null

    abstract class BaseViewHolder<T>(
        open val binding: ViewDataBinding,
        open val themeModel: Any? = null,
    ) : RecyclerView.ViewHolder(binding.root) {

        protected var currentItem: T? = null

        open var onItemClickListener: ((ItemClick) -> Unit)? = null

        @CallSuper
        open fun bind(position: Int, item: T) {
            currentItem = item
        }

        open fun bind(position: Int, item: T, payloads: MutableList<Any>) {
            currentItem = item
            bind(position, item)
        }

        fun executePendingBindings() {
            binding.executePendingBindings()
        }
    }

    abstract class BaseDiffCallback<T> : DiffUtil.ItemCallback<T>() {

        override fun areItemsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
            return false
        }
    }

    data class ItemClick(
        val id: Int = 0,
        val position: Int = 0,
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return createViewHolder(parent, LayoutInflater.from(parent.context), viewType).apply {
            this.onItemClickListener = this@BaseListAdapter.onItemClickListener
        }
    }

    abstract fun createViewHolder(
        parent: ViewGroup,
        layoutInflater: LayoutInflater,
        viewType: Int,
    ): BaseViewHolder<T>

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(position, getItem(position))
        holder.executePendingBindings()
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int, payloads: MutableList<Any>) {
        holder.bind(position, getItem(position), payloads)
        holder.executePendingBindings()
    }

    public override fun getItem(position: Int): T {
        return super.getItem(position)
    }
}
