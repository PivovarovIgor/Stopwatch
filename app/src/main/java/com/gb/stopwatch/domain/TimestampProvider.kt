package com.gb.stopwatch.domain

interface TimestampProvider {
    fun getMilliseconds(): Long
}