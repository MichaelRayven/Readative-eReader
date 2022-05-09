package com.example.usecase.books.util

sealed class BookOrder(val orderType: OrderType) {
    class Title(orderType: OrderType): BookOrder(orderType)
    class DateRead(orderType: OrderType): BookOrder(orderType)
    class DateAdded(orderType: OrderType): BookOrder(orderType)
    class DatePublished(orderType: OrderType): BookOrder(orderType)
}