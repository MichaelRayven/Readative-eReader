package com.example.readative.room.entity;

import androidx.room.Embedded;

import com.example.readative.room.entity.Series;

public class PartOfSeries {
    @Embedded public Series series;
    public int part;
}
