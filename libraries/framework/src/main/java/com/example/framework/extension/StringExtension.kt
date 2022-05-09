package com.example.framework.extension

fun String?.whenNonNull(default: Any? = Unit, callback: (String) -> Any?): Any? {
    return if(this != null) callback(this) else default
}