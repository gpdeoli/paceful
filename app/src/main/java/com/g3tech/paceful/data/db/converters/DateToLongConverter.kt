package com.g3tech.paceful.data.db.converters

import androidx.room.TypeConverter
import java.util.Date

class DateToLongConverter {
    @TypeConverter
    fun fromTimeStamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}