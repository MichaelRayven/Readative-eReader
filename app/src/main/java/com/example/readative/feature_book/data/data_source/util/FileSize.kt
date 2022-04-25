package com.example.readative.feature_book.data.data_source.util


import com.example.readative.feature_book.data.data_source.util.SizeUnit
import kotlin.math.log
import kotlin.math.pow
import kotlin.math.round

class FileSize(byteSize: Long = 0) {
    var size: Float
    var unit: SizeUnit

    init {
        val exponent = log(1024.0, byteSize.toDouble()).toInt()
        unit = SizeUnit.fromValue(exponent) ?: SizeUnit.Bytes
        size = (round(byteSize / 1024.0.pow(unit.exponent) * 100) / 100).toFloat()
    }
}

