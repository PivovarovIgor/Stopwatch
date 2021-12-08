package com.gb.stopwatch.usecases

import kotlinx.coroutines.flow.StateFlow

interface IStopwatchOrchestrator {
    fun start()
    fun pause()
    fun stop()
    val ticker: StateFlow<String>
}