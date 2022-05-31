package com.example.reading.util

data class ProgressHolder(
    var pageCount: Int = 1
) {
    var currentPage: Int = 1
        set(value) {
            field = putPageIntoRange(value)
        }

    private fun putPageIntoRange(page: Int): Int {
        return when {
            page < 1 -> {
                1
            }
            page > pageCount -> {
                pageCount
            }
            else -> {
                page
            }
        }
    }
}