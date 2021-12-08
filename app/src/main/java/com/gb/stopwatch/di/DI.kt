package com.gb.stopwatch.di

import com.gb.stopwatch.domain.*
import com.gb.stopwatch.usecases.IStopwatchOrchestrator
import com.gb.stopwatch.usecases.StopwatchOrchestrator
import com.gb.stopwatch.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object DI {

    val mainModule = module {

        single<TimestampProvider> {
            object : TimestampProvider {
                override fun getMilliseconds(): Long {
                    return System.currentTimeMillis()
                }
            }
        }
        factory { ElapsedTimeCalculator(timestampProvider = get()) }
        factory {
            StopwatchStateCalculator(
                timestampProvider = get(),
                elapsedTimeCalculator = get()
            )
        }
        factory { TimestampMillisecondsFormatter() }
        factory {
            StopwatchStateHolder(
                stopwatchStateCalculator = get(),
                elapsedTimeCalculator = get(),
                timestampMillisecondsFormatter = get()
            )
        }
        factory {
            CoroutineScope(
                Dispatchers.Main
                        + SupervisorJob()
            )
        }

        factory<IStopwatchOrchestrator> {
            StopwatchOrchestrator(
                stopwatchStateHolder = get(),
                scope = get()
            )
        }

        viewModel { MainViewModel(stopwatchOrchestrator = get()) }
    }

}