package com.example.model.local.util


import kotlin.math.log
import kotlin.math.pow
import kotlin.math.round

class FileSize(private val byteSize: Long = 0) {
    var value: Float
    var unit: SizeUnit

    init {
        val exponent = log(byteSize.toDouble(), 1024.0).toInt()
        unit = SizeUnit.fromValue(exponent) ?: SizeUnit.Bytes
        value = (round(byteSize / 1024.0.pow(unit.exponent) * 100) / 100).toFloat()
    }

    fun getByteSize(): Long {
        return byteSize
    }

    override fun toString(): String {
        return "$value $unit"
    }
}

