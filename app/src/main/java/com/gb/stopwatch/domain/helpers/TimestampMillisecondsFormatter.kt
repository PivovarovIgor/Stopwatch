package com.gb.stopwatch.domain.helpers

class TimestampMillisecondsFormatter {

    fun format(timestamp: Long): String {
        val millisecondsFormatted = (timestamp % THOUSAND).pad(DESIRED_LENGTH_THREE)
        val seconds = timestamp / THOUSAND
        val secondsFormatted = (seconds % SIXTY).pad(DESIRED_LENGTH_TWO)
        val minutes = seconds / SIXTY
        val minutesFormatted = (minutes % SIXTY).pad(DESIRED_LENGTH_TWO)
        val hours = minutes / SIXTY
        return if (hours > 0) {
            val hoursFormatted = (minutes / SIXTY).pad(DESIRED_LENGTH_TWO)
            "$hoursFormatted:$minutesFormatted:$secondsFormatted"
        } else {
            "$minutesFormatted:$secondsFormatted:$millisecondsFormatted"
        }
    }

    private fun Long.pad(desiredLength: Int) = this.toString().padStart(desiredLength, '0')

    companion object {
        const val DEFAULT_TIME = "00:00:000"
        private const val SIXTY = 60
        private const val THOUSAND = 1000
        private const val DESIRED_LENGTH_TWO = 2
        private const val DESIRED_LENGTH_THREE = 3
    }
}