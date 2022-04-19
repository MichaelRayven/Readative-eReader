package com.example.readative.util;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String bookStatusToString(BookStatus status) {
        return status.toString();
    }

    @TypeConverter
    public static BookStatus stringToBookStatus(String value) {
        try {
            return BookStatus.valueOf(value);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return BookStatus.UNREAD;
        }
    }

    public enum BookStatus {
        UNREAD, READING, FINISHED;
    }
}
