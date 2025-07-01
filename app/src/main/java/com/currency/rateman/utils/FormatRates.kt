package com.currency.rateman.utils

fun Double.formatRate(): String {
    return this.toString().trimEnd('0').trimEnd('.')
}