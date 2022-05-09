package com.example.model.local.util

enum class SizeUnit(val exponent: Int) {
    Bytes(0),
    KB(1),
    MB(2),
    GB(3),
    TB(4);

    companion object {
        private val map: MutableMap<Int, SizeUnit> = HashMap()

        init {
            for (i in values()) {
                map[i.exponent] = i
            }
        }

        fun fromValue(value: Int): SizeUnit? {
            return map[value]
        }
    }
}