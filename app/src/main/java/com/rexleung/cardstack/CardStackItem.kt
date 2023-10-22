package com.rexleung.cardstack

/**
 * Created by rexchung on 22/10/2023.
 */
data class CardStackItem(
    val id: Int,
    val color: Int,
    val selected: Boolean = false,
    val collapsed: Boolean = false,
)