package com.gongfu.recoverycompanion.logs.domain.util

sealed class LogOrder(val orderType: OrderType) {

    class Date(orderType: OrderType) : LogOrder(orderType)

    class IntensityLevel(orderType: OrderType) : LogOrder(orderType)

    fun copy(orderType: OrderType): LogOrder {
        return when(this) {
            is Date -> Date(orderType)
            is IntensityLevel -> IntensityLevel(orderType)
        }
    }
}