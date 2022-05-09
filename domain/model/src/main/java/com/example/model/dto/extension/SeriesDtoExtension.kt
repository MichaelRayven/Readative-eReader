package com.example.model.dto.extension

import com.example.model.dto.SeriesDto
import com.example.model.local.entity.Series

fun List<Series>.toSeriesDto() = map { it.toSeriesDto() }

fun Series.toSeriesDto() = SeriesDto(
    id = id,
    name = name,
    part = null
)