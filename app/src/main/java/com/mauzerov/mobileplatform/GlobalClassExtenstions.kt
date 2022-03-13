package com.mauzerov.mobileplatform

fun Boolean.toInt() : Int = if (this) 1 else 0
fun Boolean.toLong() : Long = if (this) 1 else 0
fun Boolean.toShort() : Short = if (this) 1 else 0
fun Boolean.toByte() : Byte = if (this) 1 else 0

fun <T>T.between(minimumValue: T, maximumValue: T): Boolean where T : Comparable<T>, T: Number {
    return minimumValue < this && this < maximumValue
}

fun <T>T.clamp(minimumValue: T, maximumValue: T): T where T : Comparable<T>, T: Number {
    assert(minimumValue <= maximumValue)

    return if (minimumValue < this && this < maximumValue) this
           else if (this < minimumValue) minimumValue
           else maximumValue
}