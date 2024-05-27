package com.funy4.mymicromoney.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import java.lang.reflect.Type

class LenientJsonAdapterFactory : JsonAdapter.Factory {
    override fun create(type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi): JsonAdapter<*>? {
        val adapter = moshi.nextAdapter<Any>(this, type, annotations)
        return object : JsonAdapter<Any>() {
            override fun fromJson(reader: JsonReader): Any? {
                reader.isLenient = true
                return adapter.fromJson(reader)
            }

            override fun toJson(writer: com.squareup.moshi.JsonWriter, value: Any?) {
                adapter.toJson(writer, value)
            }
        }
    }
}