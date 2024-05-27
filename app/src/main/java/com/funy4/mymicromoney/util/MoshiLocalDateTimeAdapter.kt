package com.funy4.mymicromoney.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MoshiLocalDateTimeAdapter {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @FromJson
    fun fromJson(json: String): LocalDateTime {
        return LocalDateTime.parse(json, formatter)
    }

    @ToJson
    fun toJson(value: LocalDateTime): String {
        return value.format(formatter)
    }
}