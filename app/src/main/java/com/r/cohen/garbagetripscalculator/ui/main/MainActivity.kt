package com.r.cohen.garbagetripscalculator.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.r.cohen.garbagetripscalculator.R
import com.r.cohen.garbagetripscalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // binding with ViewModel
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // observe event from the ViewModel
        viewModel.errorEvent.observe(this) {
            it?.handle()?.let { errorMsg ->
                displayErrorMsg(errorMsg)
            }
        }

        setContentView(binding.root)
    }

    private fun displayErrorMsg(errorMsg: String) {
        Toast
            .makeText(this, String.format(resources.getString(R.string.general_error), errorMsg), Toast.LENGTH_SHORT)
            .show()
    }
}