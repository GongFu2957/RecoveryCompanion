package com.gongfu.recoverycompanion.logs.domain.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}