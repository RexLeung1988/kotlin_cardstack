package com.rexleung.cardstack

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rexleung.cardstack.databinding.ViewCardStackItemBinding

/**
 * Created by rexchung on 22/10/2023.
 */
class CardStackAdapter(private val itemOnClickListener: ItemOnClickListener) : BaseListAdapter<CardStackItem>(CardStackItemItemDiff()) {
    companion object {
        private const val PAYLOAD_SELECT = "PAYLOAD_SELECT"
    }

    interface ItemOnClickListener {
        fun onClickItem(item: CardStackItem)
    }

    inner class ItemDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State,
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            val itemPosition = parent.getChildAdapterPosition(view)
            val isCollapsedItem = if (itemCount > 0) {
                getItem(0).collapsed
            } else {
                false
            }
            val collapsed = -300

            when (itemPosition) {
                0 -> {
                    if (isCollapsedItem) {
                        outRect.bottom = 600
                    } else {
                        outRect.bottom = 0
                    }
                    outRect.top = 0
                }

                else -> {
                    outRect.top = collapsed
                    outRect.bottom = 0
                }
            }
        }
    }

    fun getCardStackItemDecoration(): RecyclerView.ItemDecoration {
        return ItemDecoration()
    }

    override fun createViewHolder(
        parent: ViewGroup,
        layoutInflater: LayoutInflater,
        viewType: Int,
    ): BaseViewHolder<CardStackItem> {
        return WorkSpaceConfirmItemViewHolder(
            ViewCardStackItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    inner class WorkSpaceConfirmItemViewHolder(
        override val binding: ViewCardStackItemBinding,
    ) : BaseViewHolder<CardStackItem>(binding) {

        override fun bind(position: Int, item: CardStackItem) {
            super.bind(position, item)

            binding.vView.setBackgroundColor(ContextCompat.getColor(binding.root.context, item.color))
            binding.tvText.text = item.id.toString()

            binding.root.setOnClickListener {
                itemOnClickListener.onClickItem(item)
            }
        }

        override fun bind(position: Int, item: CardStackItem, payloads: MutableList<Any>) {
            if (payloads.isEmpty()) {
                super.bind(position, item, payloads)
            } else {
                binding.root.setOnClickListener {
                    itemOnClickListener.onClickItem(item)
                }
            }
        }
    }

    private class CardStackItemItemDiff : BaseDiffCallback<CardStackItem>() {
        override fun areItemsTheSame(
            oldItem: CardStackItem,
            newItem: CardStackItem,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CardStackItem,
            newItem: CardStackItem,
        ): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: CardStackItem, newItem: CardStackItem): Any? {
            return PAYLOAD_SELECT
        }
    }
}
