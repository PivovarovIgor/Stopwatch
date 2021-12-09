package com.gb.stopwatch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gb.stopwatch.databinding.FragmentTimerBinding
import com.gb.stopwatch.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TimerFragment : Fragment() {

    companion object {
        fun newInstance() = TimerFragment()
    }

    private val viewModel: MainViewModel by viewModel()
    private var binding: FragmentTimerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentTimerBinding.inflate(inflater, container, false)
        .also { binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveData.observe(viewLifecycleOwner, ::renderData)

        binding?.run {
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
        binding?.textTime?.text = state
    }
}