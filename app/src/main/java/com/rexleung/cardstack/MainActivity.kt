package com.rexleung.cardstack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val oriList = listOf<CardStackItem>(
        CardStackItem(0, R.color.blue),
        CardStackItem(1, R.color.red),
        CardStackItem(2, R.color.green),
        CardStackItem(3, R.color.yellow),
        CardStackItem(4, R.color.blue),
        CardStackItem(5, R.color.red),
        CardStackItem(6, R.color.green),
        CardStackItem(7, R.color.yellow),
        CardStackItem(8, R.color.blue),
        CardStackItem(9, R.color.red),
        CardStackItem(11, R.color.green),
        CardStackItem(12, R.color.yellow),
        CardStackItem(13, R.color.blue),
        CardStackItem(14, R.color.red),
        CardStackItem(15, R.color.green),
        CardStackItem(16, R.color.yellow),
    )

    private val curList = oriList.toMutableList()

    private var nsv: NestedScrollView? = null
    private var list: RecyclerView? = null

    private val itemOnClickListener = object : CardStackAdapter.ItemOnClickListener {
        override fun onClickItem(item: CardStackItem) {
            when (item.selected) {
                false -> {
                    // Select New Item
                    val newList = oriList.toMutableList()
                    newList.removeIf {
                        it.id == item.id
                    }
                    newList.add(0, item.copy(selected = true, collapsed = true))
                    (list?.adapter as? CardStackAdapter)?.submitList(
                        newList,
                    ) {
                        curList.clear()
                        curList.addAll(newList)
                    }

                    nsv?.smoothScrollTo(0, 0)
                }

                true -> {
                    // Select Exist Item
                    val newList = curList.toMutableList()
                    newList.removeIf {
                        it.id == item.id
                    }
                    newList.add(0, item.copy(selected = true, collapsed = item.collapsed.not()))
                    (list?.adapter as? CardStackAdapter)?.submitList(
                        newList,
                    ) {
                        curList.clear()
                        curList.addAll(newList)
                    }

                    nsv?.smoothScrollTo(0, 0)
                }
            }
        }
    }

    private val adapter = CardStackAdapter(
        itemOnClickListener = itemOnClickListener,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nsv = findViewById<NestedScrollView>(R.id.nsv)

        list = findViewById<RecyclerView>(R.id.rvList)
        list?.layoutManager = CardStackLayoutManager(this)
        list?.adapter = adapter
        list?.addItemDecoration(adapter.getCardStackItemDecoration())
        list?.itemAnimator = DefaultItemAnimator().apply {
            moveDuration = 300
        }

        (list?.adapter as? CardStackAdapter)?.submitList(
            curList,
        )
    }
}
