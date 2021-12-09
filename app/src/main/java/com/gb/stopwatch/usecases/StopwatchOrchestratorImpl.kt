package com.gb.stopwatch.usecases

import com.gb.stopwatch.domain.StopwatchStateHolder
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StopwatchOrchestratorImpl(
    private val stopwatchStateHolder: StopwatchStateHolder,
    private val coroutineScope: CoroutineScope,
) : IStopwatchOrchestrator {

    private var job: Job? = null
    private val mutableTicker = MutableStateFlow(stopwatchStateHolder.getStringTimeRepresentation())
    override val ticker: StateFlow<String> = mutableTicker

    override fun start() {
        startJob()
        stopwatchStateHolder.start()
    }

    private fun startJob() {
        if (job != null) {
            return
        }
        job = coroutineScope.launch {
            while (isActive) {
                mutableTicker.value = stopwatchStateHolder.getStringTimeRepresentation()
                delay(UPDATE_DELAY)
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
        mutableTicker.value = stopwatchStateHolder.getStringTimeRepresentation()
    }

    companion object {
        private const val UPDATE_DELAY = 20L
    }
}