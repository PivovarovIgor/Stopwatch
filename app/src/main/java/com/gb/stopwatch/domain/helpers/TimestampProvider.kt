package com.gb.stopwatch.domain.helpers

interface TimestampProvider {
    fun getMilliseconds(): Long
}