package com.currency.rateman.core.utils

fun Double.formatRate(): String {
    return this.toString().trimEnd('0').trimEnd('.')
}