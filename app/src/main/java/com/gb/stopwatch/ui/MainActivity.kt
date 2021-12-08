package com.gb.stopwatch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gb.stopwatch.databinding.ActivityMainBinding
import com.gb.stopwatch.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.liveData.observe(this, ::renderData)

        with(binding) {
            buttonStart.setOnClickListener {
                viewModel.start()
            }
            buttonPause.setOnClickListener {
                viewModel.pause()
            }
            buttonStop.setOnClickListener {
                viewModel.stop()
            }
        }
    }

    private fun renderData(state: String) {
        binding.textTime.text = state
    }
}