package com.gb.stopwatch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.stopwatch.usecases.IStopwatchOrchestrator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val stopwatchOrchestrator: IStopwatchOrchestrator) : ViewModel() {

    private val _liveData: MutableLiveData<String> = MutableLiveData()
    val liveData: LiveData<String> get() = _liveData

    init {
        CoroutineScope(
            Dispatchers.Main
                    + SupervisorJob()
        ).launch {
            stopwatchOrchestrator.ticker.collect {
                _liveData.postValue(it)
            }
        }
    }

    fun start() = stopwatchOrchestrator.start()

    fun pause() = stopwatchOrchestrator.pause()

    fun stop() = stopwatchOrchestrator.stop()
}