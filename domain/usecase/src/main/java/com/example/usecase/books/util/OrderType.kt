package com.example.usecase.books.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}