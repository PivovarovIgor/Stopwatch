package com.gb.stopwatch.domain

import com.gb.stopwatch.usecases.IStopwatchOrchestrator
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StopwatchOrchestratorImpl(
    private val stopwatchStateHolder: StopwatchStateHolder,
    private val coroutineScope: CoroutineScope,
) : IStopwatchOrchestrator {

    private var job: Job? = null
    private val mutableTicker = MutableStateFlow("")
    override val ticker: StateFlow<String> = mutableTicker

    override fun start() {
        if (job == null) startJob()
        stopwatchStateHolder.start()
    }

    private fun startJob() {
        coroutineScope.launch {
            while (isActive) {
                mutableTicker.value = stopwatchStateHolder.getStringTimeRepresentation()
                delay(20)
            }
        }
    }

    override fun pause() {
        stopwatchStateHolder.pause()
        stopJob()
    }

    override fun stop() {
        stopwatchStateHolder.stop()
        stopJob()
        clearValue()
    }

    private fun stopJob() {
        coroutineScope.coroutineContext.cancelChildren()
        job = null
    }

    private fun clearValue() {
        mutableTicker.value = "00:00:000"
    }
}