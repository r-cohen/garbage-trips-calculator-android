package com.r.cohen.garbagetripscalculator.ui.main

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.r.cohen.garbagetripscalculator.repos.GarbageTripsCalculatorAPIRepo
import com.r.cohen.garbagetripscalculator.ui.tools.ViewModelEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    // data-bound variables: see the view (layout) for the data-binding with view attributes
    val isCalculating = MediatorLiveData(false)
    val weightsInputText = MediatorLiveData("")
    val errorEvent = MutableLiveData<ViewModelEvent<String>>()
    val minimumTripsCountResult = MutableLiveData<Int>()

    // handles clicke of the button from the view
    fun onCalculateClick() {
        if (isCalculating.value == true || weightsInputText.value.isNullOrEmpty()) {
            return
        }

        // parses the input into a List<Float>
        val weightsList = getWeightsListFromInput(weightsInputText.value) ?: return

        // start the calculate call
        isCalculating.postValue(true)
        minimumTripsCountResult.postValue(null)

        // launch api call in IO thread pool
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val minTripsCount = GarbageTripsCalculatorAPIRepo.calculateMinTripsCount(weightsList)
                minimumTripsCountResult.postValue(minTripsCount)
            } catch (e: Exception) {
                e.message?.let { errorEvent.postValue(ViewModelEvent(it)) }
            }
            isCalculating.postValue(false)
        }
    }

    private fun getWeightsListFromInput(input: String?): List<Float>? {
        input?.let { inputString ->
            try {
                val weights = inputString.split(",").map { it.toFloat() }

                if (weights.any { it < 1.01 || it > 3.0 }) {
                    errorEvent.postValue(ViewModelEvent("invalid weight range"))
                    return null
                }

                return weights
            } catch (e: NumberFormatException) {
                errorEvent.postValue(ViewModelEvent("invalid input"))
            }
        }
        return null
    }
}