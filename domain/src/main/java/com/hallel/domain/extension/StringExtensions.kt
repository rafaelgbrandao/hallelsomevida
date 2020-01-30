package com.hallel.domain.extension

fun String.clearText(regex: String): String {
    return this.replace(Regex(regex), "")
}